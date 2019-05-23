# nifi-hikari
NiFi Custom Controller Service Development Training by Jeff Markham

## Getting Started
These guides are based on IntelliJ. NiFi version 1.9.2.

New Project > Maven > Create from archetype > Add archetype ...
* groupId = org.apache.nifi
* artifactId = nifi-service-bundle-archetype
* version = 1.9.2 (NiFi version)
* repository = http://repo.maven.apache.org/maven2/archetype-catalog.xml
* Need to set artifactBaseName as dbcp

## Postgresql Setup
```
sudo apt-get install postgresql
sudo apt-get install postgresql-contrib
sudo service postgresql start
```

### Pgadmin4 Setup & Run
```
sudo apt-get install virtualenv python-pip libpq-dev python-dev
virtualenv pgadmin4
cd pgadmin4
source bin/activate
wget https://ftp.postgresql.org/pub/pgadmin/pgadmin4/v4.6/pip/pgadmin4-4.6-py2.py3-none-any.whl
pip install pgadmin4-4.6-py2.py3-none-any.whl
python lib/python2.7/site-packages/pgadmin4/pgAdmin4.py
```
