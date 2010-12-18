package org.ourproject.kune.platf.integration.content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.docs.server.DocumentServerTool;
import org.ourproject.kune.platf.integration.IntegrationTestHelper;
import org.ourproject.kune.wiki.server.WikiServerTool;

import cc.kune.core.client.errors.ContentNotFoundException;
import cc.kune.core.client.errors.UserMustBeLoggedException;
import cc.kune.core.shared.domain.utils.AccessRights;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.ContainerDTO;
import cc.kune.core.shared.dto.ContainerSimpleDTO;
import cc.kune.core.shared.dto.ContentSimpleDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.dto.StateContentDTO;

public class ContentServiceAddTest extends ContentServiceIntegrationTest {

    private StateContentDTO defaultContent;

    @Before
    public void init() throws Exception {
        new IntegrationTestHelper(this);
    }

    @Test(expected = UserMustBeLoggedException.class)
    public void noLoggedInShouldThrowIllegalAccess() throws ContentNotFoundException, Exception {
        defaultContent = getSiteDefaultContent();
        contentService.addContent(session.getHash(), defaultContent.getContainer().getStateToken(), "a name",
                DocumentServerTool.TYPE_DOCUMENT);
    }

    @Test
    public void testAddContent() throws Exception {
        doLogin();
        defaultContent = getSiteDefaultContent();
        assertEquals(1, defaultContent.getContainer().getContents().size());
        final AccessRights cntRights = defaultContent.getContentRights();
        final AccessRights ctxRights = defaultContent.getContainerRights();
        final AccessRights groupRights = defaultContent.getGroupRights();

        final String title = "New Content Title";
        final StateContentDTO added = contentService.addContent(session.getHash(),
                defaultContent.getContainer().getStateToken(), title, DocumentServerTool.TYPE_DOCUMENT);
        assertNotNull(added);
        final List<ContentSimpleDTO> contents = added.getContainer().getContents();
        assertEquals(title, added.getTitle());
        assertEquals(2, contents.size());
        assertEquals(cntRights, added.getContentRights());
        assertEquals(ctxRights, added.getContainerRights());
        assertEquals(groupRights, added.getGroupRights());
        assertNotNull(added.getGroupMembers());
        assertNotNull(added.getParticipation());
        assertNotNull(added.getAccessLists());

        final StateToken newState = added.getStateToken();
        final StateContentDTO sameAgain = (StateContentDTO) contentService.getContent(session.getHash(), newState);
        assertNotNull(sameAgain);
        assertEquals(2, sameAgain.getContainer().getContents().size());
    }

    @Test
    public void testAddFolder() throws Exception {
        doLogin();
        defaultContent = getSiteDefaultContent();
        final ContainerDTO parent = defaultContent.getContainer();
        final String title = "folder name";
        final StateContainerDTO newState = contentService.addFolder(session.getHash(), parent.getStateToken(), title,
                DocumentClientTool.TYPE_FOLDER);
        assertNotNull(newState);
        assertNotNull(newState.getGroupMembers());
        assertNotNull(newState.getParticipation());
        assertNotNull(newState.getAccessLists());
        assertNotNull(newState.getContainerRights());
        assertNotNull(newState.getGroupRights());
        assertNotNull(newState.getRootContainer().getContents().get(0).getRights());

        final ContainerDTO parentAgain = getSiteDefaultContent().getContainer();
        final ContainerSimpleDTO child = parentAgain.getChilds().get(0);
        assertEquals(parent.getId(), child.getParentFolderId());

        assertEquals(parent.getId(), parentAgain.getId());
        assertEquals(1, parentAgain.getChilds().size());
    }

    // @Test
    public void testAddRoom() throws Exception {
        doLogin();
        defaultContent = getSiteDefaultContent();
        final ContainerDTO parent = defaultContent.getContainer();
        final String roomName = "testroom";
        final StateContainerDTO newState = contentService.addRoom(session.getHash(), parent.getStateToken(), roomName);
        assertNotNull(newState);
    }

    @Test
    public void testAddTwoFolders() throws Exception {
        doLogin();
        defaultContent = getSiteDefaultContent();
        final ContainerDTO parent = defaultContent.getContainer();
        final String title = "folder name";
        final StateContainerDTO newState = contentService.addFolder(session.getHash(), parent.getStateToken(), title,
                DocumentClientTool.TYPE_FOLDER);
        assertNotNull(newState);

        final StateContainerDTO newState2 = contentService.addFolder(session.getHash(), parent.getStateToken(), title,
                DocumentClientTool.TYPE_FOLDER);
        assertNotNull(newState2);

        final ContainerDTO parentAgain = getSiteDefaultContent().getContainer();

        assertEquals(parent.getId(), parentAgain.getId());
        assertEquals(2, parentAgain.getChilds().size());
    }

    @Test
    public void testAddWikiContent() throws Exception {
        doLogin();

        final StateToken wikiToken = new StateToken(super.getDefSiteGroupName(), WikiServerTool.NAME);
        final StateContainerDTO wiki = (StateContainerDTO) contentService.getContent(session.getHash(), wikiToken);

        final String title = "New wikipage";
        final StateContentDTO added = contentService.addContent(session.getHash(), wiki.getStateToken(), title,
                WikiServerTool.TYPE_WIKIPAGE);
        assertNotNull(added);
        final ContainerDTO wikiContainer = added.getContainer();
        final List<ContentSimpleDTO> contents = wikiContainer.getContents();
        assertEquals(title, added.getTitle());
        assertEquals(2, contents.size());
        doLogout();

        doLoginWithDummyUser();
        contentService.save(getHash(), added.getStateToken(), "some new test");
        // assertEquals(cntRights, added.getContentRights());
        // assertEquals(ctxRights, added.getContainerRights());
        // assertEquals(groupRights, added.getGroupRights());
        // assertNotNull(added.getGroupMembers());
        // assertNotNull(added.getParticipation());
        // assertNotNull(added.getAccessLists());
    }
}
