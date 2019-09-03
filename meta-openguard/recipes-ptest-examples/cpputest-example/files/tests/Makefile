#---------
#
# CppUTest Examples Makefile
#
#----------

#Set this to @ to keep the makefile quiet
ifndef SILENCE
	SILENCE = @
endif

#--- Inputs ----#
CPPUTEST_HOME = ..

CPPUTEST_USE_EXTENSIONS = Y
CPP_PLATFORM = Gcc

# Set the system flags otherwise they are overwritten in MakefileWoker.mk
CPPUTEST_ADDITIONAL_CXXFLAGS = ${TARGET_CXXFLAGS}
CPPUTEST_ADDITIONAL_CPPFLAGS = ${TARGET_CPPFLAGS}
CPPUTEST_ADDITIONAL_CFLAGS = ${TARGET_CFLAGS}

# This line is overriding the default new macros.  This is helpful
# when using std library includes like <list> and other containers
# so that memory leak detection does not conflict with stl.
# CPPUTEST_MEMLEAK_DETECTOR_NEW_MACRO_FILE = -include ApplicationLib/ExamplesNewOverrides.h

SRC_DIRS = ..

TEST_SRC_DIRS = .

INCLUDE_DIRS =\
  .\
  ..\
  $(CPPUTEST_HOME)/include


include $(CPPUTEST_HOME)/build/MakefileWorker.mk


