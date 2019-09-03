from setuptools import setup, find_packages


with open('README.rst') as f:
    readme = f.read()

with open('LICENSE') as f:
    license = f.read()

setup(
    name='coverage_example',
    version='0.1.0',
    description='Sample package to show the python code coverage feature in the CI.',
    long_description=readme,
    author='Erik Nellessen',
    author_email='enellessen@phoenixcontact.com',
    url='https://pxccs-gitlab.dev.de.innominate.com/openguard/openguard',
    license=license,
    packages=find_packages(exclude=('tests', 'docs'))
)
