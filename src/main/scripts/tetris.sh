#!/bin/bash
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

# ----------------------------------------------------------------------------
# TETRIS Start Up Batch script
#
# Required ENV vars:
# ------------------
#   JAVA_HOME - location of a JDK home dir
#
# ----------------------------------------------------------------------------

# OS specific support.  $var _must_ be set to either true or false.
cygwin=false;
darwin=false;
mingw=false
case "`uname`" in
  CYGWIN*) cygwin=true ;;
  MINGW*) mingw=true;;
  Darwin*) darwin=true
           #
           # Look for the Apple JDKs first to preserve the existing behaviour, and then look
           # for the new JDKs provided by Oracle.
           # 
           if [ -z "$JAVA_HOME" ] && [ -L /System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK ] ; then
             #
             # Apple JDKs
             #
             export JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home
           fi
           
           if [ -z "$JAVA_HOME" ] && [ -L /System/Library/Java/JavaVirtualMachines/CurrentJDK ] ; then
             #
             # Apple JDKs
             #
             export JAVA_HOME=/System/Library/Java/JavaVirtualMachines/CurrentJDK/Contents/Home
           fi
             
           if [ -z "$JAVA_HOME" ] && [ -L "/Library/Java/JavaVirtualMachines/CurrentJDK" ] ; then
             #
             # Oracle JDKs
             #
             export JAVA_HOME=/Library/Java/JavaVirtualMachines/CurrentJDK/Contents/Home
           fi           

           if [ -z "$JAVA_HOME" ] && [ -x "/usr/libexec/java_home" ]; then
             #
             # Apple JDKs
             #
             export JAVA_HOME=`/usr/libexec/java_home`
           fi
           ;;
esac

if [ -z "$JAVA_HOME" ] ; then
  if [ -r /etc/gentoo-release ] ; then
    JAVA_HOME=`java-config --jre-home`
  fi
fi

if [ -z "$TETRIS_HOME" ] ; then
  ## resolve links - $0 may be a link to maven's home
  PRG="$0"

  # need this for relative symlinks
  while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
      PRG="$link"
    else
      PRG="`dirname "$PRG"`/$link"
    fi
  done

  saveddir=`pwd`

  TETRIS_HOME=`dirname "$PRG"`/..

  # make it fully qualified
  TETRIS_HOME=`cd "$TETRIS_HOME" && pwd`

  cd "$saveddir"
fi

# For Cygwin, ensure paths are in UNIX format before anything is touched
if $cygwin ; then
  [ -n "$TETRIS_HOME" ] &&
    TETRIS_HOME=`cygpath --unix "$TETRIS_HOME"`
  [ -n "$JAVA_HOME" ] &&
    JAVA_HOME=`cygpath --unix "$JAVA_HOME"`
  [ -n "$CLASSPATH" ] &&
    CLASSPATH=`cygpath --path --unix "$CLASSPATH"`
fi

# For Migwn, ensure paths are in UNIX format before anything is touched
if $mingw ; then
  [ -n "$TETRIS_HOME" ] &&
    TETRIS_HOME="`(cd "$TETRIS_HOME"; pwd)`"
  [ -n "$JAVA_HOME" ] &&
    JAVA_HOME="`(cd "$JAVA_HOME"; pwd)`"
fi

if [ -z "$JAVA_HOME" ]; then
  javaExecutable="`which javac`"
  if [ -n "$javaExecutable" -a ! "`expr \"$javaExecutable\" : '\([^ ]*\)'`" = "no" ]; then
    # readlink(1) is not available as standard on Solaris 10.
    readLink=`which readlink`
    if [ ! `expr "$readLink" : '\([^ ]*\)'` = "no" ]; then
      javaExecutable="`readlink -f \"$javaExecutable\"`"
      javaHome="`dirname \"$javaExecutable\"`"
      javaHome=`expr "$javaHome" : '\(.*\)/bin'`
      JAVA_HOME="$javaHome"
      export JAVA_HOME
    fi
  fi
fi

if [ -z "$JAVACMD" ] ; then
  if [ -n "$JAVA_HOME"  ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
      # IBM's JDK on AIX uses strange locations for the executables
      JAVACMD="$JAVA_HOME/jre/sh/java"
    else
      JAVACMD="$JAVA_HOME/bin/java"
    fi
  else
    JAVACMD="`which java`"
  fi
fi

if [ ! -x "$JAVACMD" ] ; then
  echo "Error: JAVA_HOME is not defined correctly."
  echo "  We cannot execute $JAVACMD"
  exit 1
fi

if [ -z "$JAVA_HOME" ] ; then
  echo "Warning: JAVA_HOME environment variable is not set."
fi

# For Cygwin, switch paths to Windows format before running java
if $cygwin; then
  [ -n "$TETRIS_HOME" ] &&
    TETRIS_HOME=`cygpath --path --windows "$TETRIS_HOME"`
  [ -n "$JAVA_HOME" ] &&
    JAVA_HOME=`cygpath --path --windows "$JAVA_HOME"`
  [ -n "$CLASSPATH" ] &&
    CLASSPATH=`cygpath --path --windows "$CLASSPATH"`
fi

sedExecutable=`which sed`
if [ -n "$sedExecutable" ]; then
  javaVersion=`$JAVACMD -version 2>&1 | sed 's/.*version "\(.*\)\.\(.*\)\..*"/\1\2/; 1q'`
  if [[ $javaVersion =~ ^[1-9][0-9]$ ]]; then
     if [ "$javaVersion" -lt 18 ]; then
        echo "Unsupported Java version. TETRIS requires Java 8 and higher."
        exit 2
     fi
  else
     echo "WARNING: Unable to detect Java version."
  fi
fi

# Check for debug parameter
TETRIS_DEBUG=""
for x; do
  if [ "$x" = "-d" ]; then TETRIS_DEBUG=":${JAVA_HOME}/lib"; break; fi
  if [ "$x" = "--debug" ]; then TETRIS_DEBUG=":${JAVA_HOME}/lib"; break; fi
done

# Set the TETRIS working directory
cd "$TETRIS_HOME"

TETRIS_JAR=$(find "$TETRIS_HOME/lib" -type f -regex '.*lib/sewer-tetris-[0-9][0-9]*\.[0-9][0-9]*.*\.jar')

# Run TETRIS
exec "$JAVACMD" \
  -Dlog4j.configurationFile="${TETRIS_HOME}/log4j2.xml" \
  -Djava.util.logging.manager=org.apache.logging.log4j.jul.LogManager \
  -Djava.ext.dirs="${JAVA_HOME}/lib/ext:${JAVA_HOME}/jre/lib/ext:${TETRIS_HOME}/lib/ext:${JAVA_HOME}/lib${TETRIS_DEBUG}" \
  -jar "${TETRIS_JAR}" \
  "$@" &