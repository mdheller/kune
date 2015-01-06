package cc.kune.client;

import cc.kune.barters.client.BartersParts;
import cc.kune.blogs.client.BlogsParts;
import cc.kune.bootstrap.client.actions.gwtui.BootstrapGuiProvider;
import cc.kune.chat.client.ChatParts;
import cc.kune.common.client.events.EventBusWithLogging;
import cc.kune.common.client.shortcuts.GlobalShortcutRegister;
import cc.kune.common.client.utils.MetaUtils;
import cc.kune.common.client.utils.WindowUtils;
import cc.kune.core.client.CoreParts;
import cc.kune.core.client.actions.xml.XMLActionsParser;
import cc.kune.core.client.notify.spiner.SpinerPresenter;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.sitebar.ErrorsDialog;
import cc.kune.core.client.sitebar.logo.SiteLogo;
import cc.kune.core.client.state.SessionExpirationManager;
import cc.kune.core.client.state.SiteParameters;
import cc.kune.core.client.state.impl.SessionChecker;
import cc.kune.core.client.ws.CorePresenter;
import cc.kune.docs.client.DocsParts;
import cc.kune.events.client.EventsParts;
import cc.kune.gspace.client.GSpaceParts;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.hspace.client.HSpaceParts;
import cc.kune.lists.client.ListsParts;
import cc.kune.pspace.client.PSpaceParts;
import cc.kune.tasks.client.TasksParts;
import cc.kune.trash.client.TrashParts;
import cc.kune.wiki.client.WikiParts;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Bootstrapper;

public class KuneBootstrapper implements Bootstrapper {

  /** The Constant HOME_IDS_DEF_SUFFIX. */
  protected static final String HOME_IDS_DEF_SUFFIX = "-def";

  /** The Constant HOME_IDS_PREFIX. */
  protected static final String HOME_IDS_PREFIX = "k-home-";

  private final ContentViewerSelector contentViewerSelector;

  private final CorePresenter corePresenter;

  private final GlobalShortcutRegister globalShortcutRegister;

  private final SessionChecker sessionChecker;

  @Inject
  public KuneBootstrapper(final SessionChecker sessionChecker,
      final ContentViewerSelector contentViewerSelector,
      final GlobalShortcutRegister globalShortcutRegister, final SpinerPresenter spinerPresenter,
      final SessionExpirationManager sessionExpirationManager,
      final EventBusWithLogging eventBusWithLogging, final ErrorsDialog errorsDialog,
      final CorePresenter corePresenter, final OnAppStartFactory onAppStartFactory,

      final DocsParts docs, final BlogsParts blogs, final WikiParts wiki, final EventsParts events,
      final TasksParts tasks, final ListsParts lists, final ChatParts chats, final BartersParts barters,
      final TrashParts trash,

      final SiteLogo siteLogo,

      final CoreParts coreParts, final GSpaceParts gSpaceParts, final PSpaceParts pSpaceParts,
      final HSpaceParts hSpaceParts,

      final XMLActionsParser xmlActionsParser,

      // Here you define the gui ui provider (gwt, gxt, bootstrap)
      final BootstrapGuiProvider bootstrapProvider
  // GwtGuiProvider guiProvider
      ) {

    this.sessionChecker = sessionChecker;
    this.contentViewerSelector = contentViewerSelector;
    this.globalShortcutRegister = globalShortcutRegister;
    this.corePresenter = corePresenter;
  }

  @Override
  public void onBootstrap() {

    corePresenter.forceReveal();

    sessionChecker.check(new AsyncCallbackSimple<Void>() {
      @Override
      public void onSuccess(final Void result) {
        // Do nothing
      }
    });

    contentViewerSelector.init();

    globalShortcutRegister.enable();

    setHomeLocale();
  }

  /**
   * Home set locale. In ws.html there is some no visible elements with the
   * different locales and we only show the current locale
   */
  private void setHomeLocale() {
    final String currentLocale = WindowUtils.getParameter(SiteParameters.LOCALE);

    final String meta = MetaUtils.get("kune.home.ids");
    if (meta != null) {
      final String[] ids = meta.split(",[ ]*");

      for (final String id : ids) {
        final RootPanel someElement = RootPanel.get(HOME_IDS_PREFIX + id + "-" + currentLocale);
        final RootPanel defElement = RootPanel.get(HOME_IDS_PREFIX + id + HOME_IDS_DEF_SUFFIX);
        if (someElement != null) {
          someElement.setVisible(true);
        } else if (defElement != null) {
          defElement.setVisible(true);
        }
      }
    }
  }

}