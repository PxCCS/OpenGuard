/*
   This command will be executed if mysimple-service.service was reloaded (systemctl reload mysimple-service)
   The output can be seen with systemctl status mysimple-service.
   */

#include <syslog.h>


int main(int argc, char *argv[]) {
	syslog(LOG_NOTICE, "My reload command from simple service started\n");

	return 0;
}
