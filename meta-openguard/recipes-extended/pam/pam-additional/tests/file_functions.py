#!/usr/bin/env python3
""" summary:common functions for filehandling
    date: 2018-03-13
    author: PxCCs - HDR
    version: 0.2
"""
import os.path
from os import listdir
import re

##############################################################################
def check_files_exist(file_list):
    """Check whether the list of files exist
       Return True , when all files in the list exist
       Return False , when one file in the list doesn't exist
    """
    # all returns true if every value is true
    return all(os.path.exists(x) for x in file_list)

##############################################################################
def wipe_comment(line):
    """
     Remove the comment of a line or the whole content of the line, if it is a
     comment
    """
    # regexp to remove everything after the comment identifier
    line = re.sub(r'#.*$', '', line)
    # remove trailing spaces.
    return line.strip()

##############################################################################
def find_config_in_pam_file(filename, search_string):
    """
     Open each pam-configfile in /etc/pam.d/ and try to find the searchString
     Return true  (if the search_string is found)
      - skip comments
      false (if the search_string is not found)
    """
    with open(filename, "r") as filehandler:
        datalines = filehandler.readlines()

    ret = False
    #iterare of all lines in the file
    for line in datalines:
        #delete the comments
        line_without_comments = wipe_comment(line)
        if search_string in line_without_comments:
            ret = True
            break
        else:
            ret = False

    return ret

##############################################################################
def get_files_of_folder(folder_name):
    """
     Get all files in the directory (folder_name)
    """
    return (f for f in listdir(folder_name))
