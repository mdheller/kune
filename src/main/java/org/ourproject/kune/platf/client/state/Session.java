/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.state;

import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.dto.I18nCountryDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageSimpleDTO;
import org.ourproject.kune.workspace.client.dto.StateDTO;

/**
 * RESPONSABILITIES: - Store the user's application state - Generates URLable's
 * historyTokens
 * 
 * @author danigb
 * 
 */
public class Session {
    public String userHash;
    private List ccLicenses;
    private List notCCLicenses;
    private List languages;
    private List countries;
    private Object[][] languagesArray;
    private Object[][] countriesArray;
    private StateDTO currentState;
    private String[] wsThemes;
    private String defaultWsTheme;

    public Session(final String usersHash) {
        this.userHash = usersHash;
        ccLicenses = null;
        notCCLicenses = null;
        languages = null;
        languagesArray = null;
        countries = null;
    }

    public List getCCLicenses() {
        return ccLicenses;
    }

    public void setCCLicenses(final List licenses) {
        this.ccLicenses = licenses;
    }

    public List getNotCCLicenses() {
        return notCCLicenses;
    }

    public void setNotCCLicenses(final List licensesNotCC) {
        this.notCCLicenses = licensesNotCC;
    }

    public void setCurrent(final StateDTO currentState) {
        this.currentState = currentState;
    }

    public StateDTO getCurrentState() {
        return currentState;
    }

    public void setCurrentState(final StateDTO currentState) {
        this.currentState = currentState;
    }

    public void setDefaultWsTheme(final String defaultWsTheme) {
        this.defaultWsTheme = defaultWsTheme;

    }

    public void setWsThemes(final String[] wsThemes) {
        this.wsThemes = wsThemes;
    }

    public String[] getWsThemes() {
        return wsThemes;
    }

    public String getDefaultWsTheme() {
        return defaultWsTheme;
    }

    public boolean isLogged() {
        return userHash != null;
    }

    public List getLanguages() {
        return languages;
    }

    public void setLanguages(final List languages) {
        this.languages = languages;
    }

    public List getCountries() {
        return countries;
    }

    public void setCountries(final List countries) {
        this.countries = countries;
    }

    public Object[][] getLanguagesArray() {
        if (languagesArray == null) {
            languagesArray = mapLangs(languages);
        }
        return languagesArray;
    }

    public Object[][] getCountriesArray() {
        if (countriesArray == null) {
            countriesArray = mapCountries(countries);
        }
        return countriesArray;
    }

    private Object[][] mapCountries(final List countries) {
        Object[][] objs = new Object[countries.size()][1];
        int i = 0;
        for (Iterator iterator = countries.iterator(); iterator.hasNext();) {
            I18nCountryDTO country = (I18nCountryDTO) iterator.next();
            Object[] obj = new Object[] { country.getCode(), country.getEnglishName() };
            objs[i++] = obj;
        }
        return objs;
    }

    private Object[][] mapLangs(final List languages) {
        Object[][] objs = new Object[languages.size()][1];
        int i = 0;
        for (Iterator iterator = languages.iterator(); iterator.hasNext();) {
            I18nLanguageSimpleDTO language = (I18nLanguageSimpleDTO) iterator.next();
            Object[] obj = new Object[] { language.getCode(), language.getEnglishName() };
            objs[i++] = obj;
        }
        return objs;
    }
}
