#!/bin/bash

# must be executed from treebase/treebase-core/db/install-prefix/scripts

CMD="cp -R ../../../../treebase-web/target/treebase-web/WEB-INF/lib/ ../tb2jars/"
echo $CMD
$CMD || exit

CMD="cp -R ../../../../treebase-core/target/classes/ ../tb2classes/"
echo $CMD
$CMD || exit

