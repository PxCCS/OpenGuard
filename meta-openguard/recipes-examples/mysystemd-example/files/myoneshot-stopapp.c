/*
   This command will be executed at the stop of myoneshot-service.service
   The output can be seen with  journalctl or systemctl status myoneshot-service.service
   */

#include <syslog.h>

int main(int argc, char *argv[]) {
	syslog(LOG_NOTICE, "My second command from oneshot service started\n");
	// wait for something
	sleep(1);
	return 0;
}
