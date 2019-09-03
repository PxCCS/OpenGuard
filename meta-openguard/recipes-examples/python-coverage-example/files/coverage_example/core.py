"""This module has the core functionality of this very basic example."""

# -*- coding: utf-8 -*-
from . import helpers


def get_hmm():
    """Get a thought."""
    return 'hmmm...'


def hmm():
    """Contemplation..."""
    if helpers.get_answer():
        print(get_hmm())


def another_function():
    """This function does not have a test."""

    return 0
