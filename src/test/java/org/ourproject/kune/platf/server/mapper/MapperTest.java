package org.ourproject.kune.platf.server.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.ChatUserParamsDTO;
import org.ourproject.kune.platf.client.dto.CommentDTO;
import org.ourproject.kune.platf.client.dto.ContainerDTO;
import org.ourproject.kune.platf.client.dto.ContainerSimpleDTO;
import org.ourproject.kune.platf.client.dto.ContentDTO;
import org.ourproject.kune.platf.client.dto.CustomPropertiesDTO;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.GroupListDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.LinkDTO;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.dto.UserInfoDTO;
import org.ourproject.kune.platf.server.TestDomainHelper;
import org.ourproject.kune.platf.server.TestHelper;
import org.ourproject.kune.platf.server.access.AccessRights;
import org.ourproject.kune.platf.server.domain.ChatUserParams;
import org.ourproject.kune.platf.server.domain.Comment;
import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.CustomProperties;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.GroupList;
import org.ourproject.kune.platf.server.domain.GroupListMode;
import org.ourproject.kune.platf.server.domain.License;
import org.ourproject.kune.platf.server.domain.Revision;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.kune.platf.server.state.State;
import org.ourproject.kune.platf.server.users.UserInfo;

import com.calclab.emite.client.im.roster.RosterManager.SubscriptionMode;
import com.google.inject.Inject;

public class MapperTest {
    @Inject
    Mapper mapper;
    @Inject
    GroupManager groupManager;

    @Test
    public void customPropertiesInUserInfoMappping() {
	final UserInfo userInfo = new UserInfo();
	final ChatUserParams userChatParams = new ChatUserParams("avatar", true, SubscriptionMode.autoAcceptAll,
		"color");
	final CustomProperties customProperties = new CustomProperties();
	customProperties.setData(ChatUserParams.class, userChatParams);
	userInfo.setCustomProperties(customProperties);
	final UserInfoDTO map = mapper.map(userInfo, UserInfoDTO.class);
	final ChatUserParamsDTO data = map.getCustomProperties().getData(ChatUserParamsDTO.class);
	assertEquals("avatar", data.getAvatar());
    }

    @Test
    public void customPropertiesMappping() throws ClassNotFoundException {
	final ChatUserParams userChatParams = new ChatUserParams("avatar", true, SubscriptionMode.autoAcceptAll,
		"color");
	final CustomProperties customProperties = new CustomProperties();
	customProperties.setData(ChatUserParams.class, userChatParams);
	final CustomPropertiesDTO map = mapper.mapProperties(customProperties);
	final ChatUserParamsDTO data = map.getData(ChatUserParamsDTO.class);
	assertEquals("avatar", data.getAvatar());
    }

    @Before
    public void inject() {
	TestHelper.inject(this);
    }

    @Test
    public void testCommentMapper() {
	final Content d = createTestContent();
	final Comment comment = new Comment();
	comment.setContent(d);
	comment.setText("Some text");
	final User user = new User();
	comment.addPositiveVoter(user);
	comment.addPositiveVoter(user);
	CommentDTO commentDTO = mapper.map(comment, CommentDTO.class);
	assertEquals(0, commentDTO.getNegativeVotersCount());
	assertEquals(0, commentDTO.getAbuseInformersCount());
	assertEquals(1, commentDTO.getPositiveVotersCount());
	comment.addNegativeVoter(user);
	comment.addNegativeVoter(user);
	commentDTO = mapper.map(comment, CommentDTO.class);
	assertEquals(1, commentDTO.getNegativeVotersCount());
	assertEquals(0, commentDTO.getAbuseInformersCount());
	assertEquals(0, commentDTO.getPositiveVotersCount());
	comment.addAbuseInformer(user);
	comment.addAbuseInformer(user);
	commentDTO = mapper.map(comment, CommentDTO.class);
	assertEquals(1, commentDTO.getNegativeVotersCount());
	assertEquals(1, commentDTO.getAbuseInformersCount());
	assertEquals(0, commentDTO.getPositiveVotersCount());
	final Comment childComment = new Comment();
	childComment.setContent(d);
	childComment.setParent(comment);
	comment.getChilds().add(childComment);
	commentDTO = mapper.map(comment, CommentDTO.class);
	final CommentDTO childCommentDTO = mapper.map(childComment, CommentDTO.class);
	assertEquals(1, comment.getChilds().size());
	assertEquals(commentDTO, childCommentDTO.getParent());
    }

    @Test
    public void testContentDescriptorMapping() {
	final Content d = new Content();
	d.setId(1l);
	final Revision revision = new Revision(d);
	revision.setTitle("title");
	d.addRevision(revision);

	final ContentDTO dto = mapper.map(d, ContentDTO.class);
	assertEquals(1, (long) dto.getId());
	assertEquals("title", dto.getTitle());
    }

    @Test
    public void testContentDescriptorToLinkMapping() {
	final Content d = createTestContent();

	final LinkDTO dto = mapper.map(d, LinkDTO.class);
	assertEquals("title", dto.getLongName());
	assertEquals("grouptest", dto.getShortName());
	assertEquals("grouptest.docs.1.1", dto.getLink());
    }

    @Test
    public void testContentMapping() {
	final State c = new State();
	c.setContentRights(new AccessRights(true, true, true));
	final Group groupAdmins = TestDomainHelper.createGroup(1);
	final Group groupEdit = TestDomainHelper.createGroup(2);
	final Group groupView = TestDomainHelper.createGroup(3);
	c.setAccessLists(TestDomainHelper.createAccessLists(groupAdmins, groupEdit, groupView));
	c.setRate(10.2d);
	c.setRateByUsers(3l);
	c.setCurrentUserRate(null);

	final StateDTO dto = mapper.map(c, StateDTO.class);
	assertEquals(c.getContentRights().isAdministrable(), dto.getContentRights().isAdministrable());

	assertValidAccessListsMapping(c.getAccessLists().getAdmins(), dto.getAccessLists().getAdmins());
	assertValidAccessListsMapping(c.getAccessLists().getEditors(), dto.getAccessLists().getEditors());
	assertValidAccessListsMapping(c.getAccessLists().getViewers(), dto.getAccessLists().getViewers());

	assertEquals(dto.getRate(), c.getRate());
	assertEquals(dto.getRateByUsers(), c.getRateByUsers());
	assertEquals(dto.getCurrentUserRate(), c.getCurrentUserRate());
    }

    @Test
    public void testFolderMapping() {
	final Container container = new Container();
	container.addChild(new Container());
	container.addChild(new Container());
	container.addContent(new Content());
	container.addContent(new Content());
	container.addContent(new Content());
	final Container containerChild = new Container();
	container.addChild(containerChild);
	final List<Container> absolutePathChild = new ArrayList<Container>();
	absolutePathChild.add(container);
	containerChild.setAbsolutePath(absolutePathChild);

	final ContainerDTO dto = mapper.map(container, ContainerDTO.class);
	assertEquals(3, dto.getChilds().size());
	assertEquals(3, dto.getContents().size());
	assertTrue(dto.getContents().get(0) instanceof ContentDTO);
	assertTrue(dto.getChilds().get(0) instanceof ContainerDTO);

	final ContainerDTO dtoChild = mapper.map(containerChild, ContainerDTO.class);
	assertTrue(dtoChild.getAbsolutePath()[0] instanceof ContainerSimpleDTO);
    }

    @Test
    public void testGroupListMapping() {
	assertMapping(GroupListMode.EVERYONE, GroupListDTO.EVERYONE);
	assertMapping(GroupListMode.NOBODY, GroupListDTO.NOBODY);
	assertMapping(GroupListMode.NORMAL, GroupListDTO.NORMAL);
    }

    @Test
    public void testGroupMapping() {
	final Group group = new Group("shortName", "name");
	final GroupDTO dto = mapper.map(group, GroupDTO.class);
	assertEquals(group.getLongName(), dto.getLongName());
	assertEquals(group.getShortName(), dto.getShortName());
    }

    @Test
    public void testLicenseMapping() {
	final License licenseCC = new License("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "cc1",
		"http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "cc2", "cc3");

	final License licenseNotCC = new License("gfdl", "GNU Free Documentation License", "nocc1",
		"http://www.gnu.org/copyleft/fdl.html", false, true, false, "nocc2", "nocc3");

	final LicenseDTO dtoCC = mapper.map(licenseCC, LicenseDTO.class);
	final LicenseDTO dtoNotCC = mapper.map(licenseNotCC, LicenseDTO.class);

	assertEquals("by-nc-nd", dtoCC.getShortName());
	assertEquals("gfdl", dtoNotCC.getShortName());
	assertEquals("Creative Commons Attribution-NonCommercial-NoDerivs", dtoCC.getLongName());
	assertEquals("GNU Free Documentation License", dtoNotCC.getLongName());
	assertEquals("http://creativecommons.org/licenses/by-nc-nd/3.0/", dtoCC.getUrl());
	assertEquals("http://www.gnu.org/copyleft/fdl.html", dtoNotCC.getUrl());
	assertTrue(dtoCC.isCC());
	assertFalse(dtoNotCC.isCC());
	assertFalse(dtoCC.isCopyleft());
	assertTrue(dtoNotCC.isCopyleft());
	assertFalse(dtoCC.isDeprecated());
	assertFalse(dtoNotCC.isDeprecated());
	assertEquals("cc1", dtoCC.getDescription());
	assertEquals("cc2", dtoCC.getRdf());
	assertEquals("cc3", dtoCC.getImageUrl());
	assertEquals("nocc1", dtoNotCC.getDescription());
	assertEquals("nocc2", dtoNotCC.getRdf());
	assertEquals("nocc3", dtoNotCC.getImageUrl());
    }

    @Test
    public void testUserToLinkMappping() {
	final User user = new User("shortName", "longName", "", "", null, null, null);
	final LinkDTO dto = mapper.map(user, LinkDTO.class);
	assertEquals("shortName", dto.getShortName());
	assertEquals("longName", dto.getLongName());
    }

    private void assertMapping(final GroupListMode mode, final String modeName) {
	final GroupList list = new GroupList();
	list.setMode(mode);
	final GroupListDTO dto = mapper.map(list, GroupListDTO.class);
	assertEquals(modeName, dto.getMode());
	final GroupList listBack = mapper.map(dto, GroupList.class);
	assertEquals(mode, listBack.getMode());
    }

    private void assertValidAccessListsMapping(final GroupList groupList, final GroupListDTO groupListDTO) {
	final List<Group> listOrig = groupList.getList();
	final List<GroupDTO> listDto = groupListDTO.getList();
	assertEquals(listDto.size(), listOrig.size());
	for (int i = 0; i < listDto.size(); i++) {
	    final Object object = listDto.get(i);
	    assertEquals(GroupDTO.class, object.getClass());
	    final GroupDTO d = (GroupDTO) object;
	    final Group g = listOrig.get(i);
	    assertNotNull(d);
	    assertNotNull(g);
	    final GroupDTO map = mapper.map(g, GroupDTO.class);
	    assertEquals(map, d);
	}
    }

    private Content createTestContent() {
	final Group group = new Group("grouptest", "This is a group Test");
	final Container container = new Container();
	container.setId(1l);
	container.setToolName("docs");
	container.setOwner(group);
	container.setName("folder");
	final Content d = new Content();
	d.setId(1l);
	final Revision revision = new Revision(d);
	revision.setTitle("title");
	d.addRevision(revision);
	d.setContainer(container);
	return d;
    }
}
