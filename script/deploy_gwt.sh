#!/bin/bash
find target/kune-0.0.4/org.ourproject.kune.app.Kune/ -name .DS_Store -exec rm {} \;

#script/css-compact-and-tidy.css
script/css-update.sh

unison -silent -batch -auto -ui text target/kune-0.0.4/org.ourproject.kune.app.Kune src/main/webapp/gwt/org.ourproject.kune.app.Kune