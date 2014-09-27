#!/bin/bash
if [ "$TRAVIS_JDK_VERSION" = "oraclejdk8" ]; then
    git config --global user.name "Travis-CI"
    git config --global user.email "noreply@travis-ci.com"
    git clone https://github.com/LapisBlue/Javadocs.git .jd
    ./gradlew javadoc -x :SpongeAPI:javadoc
    cd .jd/
    git rm -r commons
    cp -r ../build/docs/javadoc commons
    git add -A
    git commit -m "Update $(date -u +"%Y-%m-%dT%H:%M:%SZ")"
    git push origin gh-pages
fi
