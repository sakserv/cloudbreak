base:
  '*':
    - discovery
    - java

  'roles:kerberos_server':
    - match: grain
    - kerberos

  'roles:ambari_server':
    - match: grain
    - ambari.server

  'roles:ambari_agent':
    - match: grain
    - ambari.agent

  'roles:smartsense':
    - match: grain
    - smartsense

  'G@recipes:post and G@roles:knox_ldap':
    - match: compound
    - ldap.knox

  'recipes:pre':
    - match: grain
    - pre-recipes

  'recipes:post':
    - match: grain
    - post-recipes
