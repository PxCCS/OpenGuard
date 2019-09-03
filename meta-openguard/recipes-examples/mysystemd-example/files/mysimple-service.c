/*
   The output can be seen with systemctl status mysimple-service
   */
#include <syslog.h>

int main(int argc, char *argv[]) {
	syslog(LOG_NOTICE, "My start command from simple service started\n");

	while(1) {
		// do someting
		// wait for something
		sleep(1);
	}
	return 0;
}
