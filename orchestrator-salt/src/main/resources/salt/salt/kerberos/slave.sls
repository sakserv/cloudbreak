{%- from 'kerberos/settings.sls' import kerberos with context %}

include:
  - {{ slspath }}.common

{% if kerberos.url is none or kerberos.url == '' %}

add_kpropd_sh_script:
  file.managed:
    - name: /tmp/kpropd.sh
    - source: salt://kerberos/scripts/kpropd.sh
    - template: jinja
    - skip_verify: True
    - makedirs: True
    - mode: 755
    - context:
      realm: {{ kerberos.realm }}
      kdcs: {{ kerberos.kdcs }}

run_kpropd_sh_script:
  cmd.run:
    - name: sh -x /tmp/kpropd.sh 2>&1 | tee -a /var/log/kpropd_sh.log && exit ${PIPESTATUS[0]}
    - require:
      - file: add_kpropd_sh_script

start_slave_kdc:
  service.running:
    - enable: True
    - name: krb5kdc
    - watch:
      - pkg: install_kerberos

stop_kadmin:
  service.dead:
    - enable: False
    - name: kadmin

{% endif %}

create_krb5_conf_initialized:
  cmd.run:
    - name: touch /var/krb5-conf-initialized
    - shell: /bin/bash
    - unless: test -f /var/krb5-conf-initialized