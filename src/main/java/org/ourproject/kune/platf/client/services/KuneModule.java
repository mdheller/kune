package org.ourproject.kune.platf.client.services;

import java.util.HashMap;

import org.ourproject.kune.platf.client.KunePlatform;
import org.ourproject.kune.platf.client.app.Application;
import org.ourproject.kune.platf.client.app.ApplicationDefault;
import org.ourproject.kune.platf.client.app.HistoryWrapper;
import org.ourproject.kune.platf.client.app.HistoryWrapperImpl;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetsManager;
import org.ourproject.kune.platf.client.rpc.AsyncCallbackSimple;
import org.ourproject.kune.platf.client.rpc.ContentService;
import org.ourproject.kune.platf.client.state.ContentProviderImpl;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.SessionImpl;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.state.StateManagerDefault;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.search.SiteSearcher;
import org.ourproject.kune.workspace.client.search.SiteSearcherPanel;
import org.ourproject.kune.workspace.client.search.SiteSearcherPresenter;
import org.ourproject.kune.workspace.client.search.SiteSearcherView;
import org.ourproject.kune.workspace.client.sitebar.Site;
import org.ourproject.kune.workspace.client.tags.TagsPresenter;
import org.ourproject.kune.workspace.client.tags.ui.TagsPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.WorkspaceManager;
import org.ourproject.kune.workspace.client.ui.newtmp.licensefoot.EntityLicensePanel;
import org.ourproject.kune.workspace.client.ui.newtmp.licensefoot.EntityLicensePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitelogo.SiteLogo;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitelogo.SiteLogoPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitelogo.SiteLogoPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesearch.SiteSearch;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesearch.SiteSearchPanel;
import org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitesearch.SiteSearchPresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePanel;
import org.ourproject.kune.workspace.client.ui.newtmp.themes.WsThemePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntitySubTitlePanel;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntitySubTitlePresenter;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntityTitlePanel;
import org.ourproject.kune.workspace.client.ui.newtmp.title.EntityTitlePresenter;
import org.ourproject.kune.workspace.client.workspace.Tags;
import org.ourproject.kune.workspace.client.workspace.ui.EntityLogo;
import org.ourproject.kune.workspace.client.workspace.ui.EntityLogoPanel;

import com.calclab.suco.client.container.Provider;
import com.calclab.suco.client.modules.Module;
import com.calclab.suco.client.modules.ModuleBuilder;
import com.calclab.suco.client.scopes.SingletonScope;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;

public class KuneModule implements Module {
    private final I18nLanguageDTO initialLang;
    private final HashMap<String, String> lexicon;

    public KuneModule(final I18nLanguageDTO initialLang, final HashMap<String, String> lexicon) {
	this.initialLang = initialLang;
	this.lexicon = lexicon;
    }

    public Class<? extends Module> getType() {
	return KuneModule.class;
    }

    public void onLoad(final ModuleBuilder builder) {
	builder.registerProvider(Kune.class, new Provider<Kune>() {
	    public Kune get() {
		return new Kune(builder);
	    }
	}, SingletonScope.class);

	builder.registerProvider(I18nUITranslationService.class, new Provider<I18nUITranslationService>() {
	    public I18nUITranslationService get() {
		final I18nUITranslationService i18n = new I18nUITranslationService();
		i18n.setCurrentLanguage(initialLang.getCode());
		i18n.setLexicon(lexicon);
		return i18n;
	    }
	}, SingletonScope.class);

	builder.registerProvider(Session.class, new Provider<Session>() {
	    public Session get() {
		return new SessionImpl(Cookies.getCookie("userHash"), initialLang);
	    }
	}, SingletonScope.class);

	final I18nUITranslationService i18n = builder.getInstance(I18nUITranslationService.class);
	builder.registerProvider(KuneErrorHandler.class, new Provider<KuneErrorHandler>() {
	    public KuneErrorHandler get() {
		return new KuneErrorHandler(builder.getInstance(Session.class), i18n);
	    }
	}, SingletonScope.class);

	final KuneErrorHandler errorHandler = builder.getInstance(KuneErrorHandler.class);
	AsyncCallbackSimple.init(errorHandler);
	Site.init(i18n);

	builder.registerProvider(ColorTheme.class, new Provider<ColorTheme>() {
	    public ColorTheme get() {
		return new ColorTheme();
	    }
	}, SingletonScope.class);

	builder.registerProvider(KunePlatform.class, new Provider<KunePlatform>() {
	    public KunePlatform get() {
		return new KunePlatform();
	    }
	}, SingletonScope.class);

	builder.registerProvider(ExtensibleWidgetsManager.class, new Provider<ExtensibleWidgetsManager>() {
	    public ExtensibleWidgetsManager get() {
		return new ExtensibleWidgetsManager();
	    }
	}, SingletonScope.class);

	builder.registerProvider(Application.class, new Provider<Application>() {
	    public Application get() {
		final Session session = builder.getInstance(Session.class);
		final ExtensibleWidgetsManager extensionPointManager = builder
			.getInstance(ExtensibleWidgetsManager.class);
		return new ApplicationDefault(session, extensionPointManager, i18n, builder
			.getInstance(ColorTheme.class), builder.getInstance(KuneErrorHandler.class));
	    }
	}, SingletonScope.class);

	builder.registerProvider(WorkspaceSkeleton.class, new Provider<WorkspaceSkeleton>() {
	    public WorkspaceSkeleton get() {
		return new WorkspaceSkeleton();
	    }
	}, SingletonScope.class);

	final WorkspaceSkeleton ws = builder.getInstance(WorkspaceSkeleton.class);

	builder.registerProvider(SiteLogo.class, new Provider<SiteLogo>() {
	    public SiteLogo get() {
		final SiteLogoPresenter presenter = new SiteLogoPresenter(builder.getInstance(Session.class));
		final SiteLogoPanel panel = new SiteLogoPanel(presenter, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SiteSearcher.class, new Provider<SiteSearcher>() {
	    public SiteSearcher get() {
		final SiteSearcherPresenter presenter = new SiteSearcherPresenter();
		final SiteSearcherView view = new SiteSearcherPanel(presenter, i18n, ws);
		presenter.init(view);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(SiteSearch.class, new Provider<SiteSearch>() {
	    public SiteSearch get() {
		final SiteSearchPresenter presenter = new SiteSearchPresenter(builder.getProvider(SiteSearcher.class));
		final SiteSearchPanel panel = new SiteSearchPanel(presenter, ws, i18n);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.getInstance(SiteSearch.class);
	builder.getInstance(SiteLogo.class);

	builder.registerProvider(EntityLogo.class, new Provider<EntityLogo>() {
	    public EntityLogo get() {
		return new EntityLogoPanel(i18n, ws);
	    }
	}, SingletonScope.class);

	builder.registerProvider(WsThemePresenter.class, new Provider<WsThemePresenter>() {
	    public WsThemePresenter get() {
		final WsThemePresenter presenter = new WsThemePresenter(builder.getInstance(Session.class));
		final WsThemePanel panel = new WsThemePanel(ws, presenter, i18n);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(EntityTitlePresenter.class, new Provider<EntityTitlePresenter>() {
	    public EntityTitlePresenter get() {
		final EntityTitlePresenter presenter = new EntityTitlePresenter(i18n, builder
			.getInstance(KuneErrorHandler.class));
		final EntityTitlePanel panel = new EntityTitlePanel(ws, presenter);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(EntitySubTitlePresenter.class, new Provider<EntitySubTitlePresenter>() {
	    public EntitySubTitlePresenter get() {
		final EntitySubTitlePresenter presenter = new EntitySubTitlePresenter(i18n);
		final EntitySubTitlePanel panel = new EntitySubTitlePanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(EntityLicensePresenter.class, new Provider<EntityLicensePresenter>() {
	    public EntityLicensePresenter get() {
		final EntityLicensePresenter presenter = new EntityLicensePresenter();
		final EntityLicensePanel panel = new EntityLicensePanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(WorkspaceManager.class, new Provider<WorkspaceManager>() {
	    public WorkspaceManager get() {
		final WorkspaceManager presenter = new WorkspaceManager(builder.getInstance(EntityLogo.class), builder
			.getInstance(EntityTitlePresenter.class), builder.getInstance(EntitySubTitlePresenter.class),
			builder.getInstance(WsThemePresenter.class), builder.getInstance(EntityLicensePresenter.class),
			builder.getInstance(Tags.class));
		return presenter;
	    }
	}, SingletonScope.class);

	builder.registerProvider(Tags.class, new Provider<Tags>() {
	    public Tags get() {
		final TagsPresenter presenter = new TagsPresenter(builder.getProvider(Session.class), builder
			.getProvider(SiteSearcher.class));
		final TagsPanel panel = new TagsPanel(presenter, i18n, ws);
		presenter.init(panel);
		return presenter;
	    }
	}, SingletonScope.class);

	// builder.registerProvider(type, new Provider<>() {},
	// SingletonScope.class);

	builder.registerProvider(StateManager.class, new Provider<StateManager>() {
	    public StateManager get() {
		final Session session = builder.getInstance(Session.class);
		final ContentProviderImpl provider = new ContentProviderImpl(ContentService.App.getInstance());
		final HistoryWrapper historyWrapper = new HistoryWrapperImpl();
		final Application application = builder.getInstance(Application.class);
		final WorkspaceManager wm = builder.getInstance(WorkspaceManager.class);
		final StateManagerDefault stateManager = new StateManagerDefault(provider, application, session,
			historyWrapper, wm);
		History.addHistoryListener(stateManager);
		return stateManager;
	    }
	}, SingletonScope.class);

    }

}
