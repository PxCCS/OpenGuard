FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

# Nginx should not have write permissions in the directory from where it
# serves static files: Change owner from www (i.e. the user of the nginx
# worker process) to www-data.
#
do_install_append () {
    chown -R www-data ${D}${NGINX_WWWDIR}
}
