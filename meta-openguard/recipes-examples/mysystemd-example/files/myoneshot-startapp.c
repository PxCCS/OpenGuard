/*
   This command will be executed at the start of myoneshot-service.service
   The output can be seen with journalctl or systemctl status myoneshot-service
   It tries to read the number of bytes currently available to be read from the kernel log buffer and writes the result to syslog. 
   If the service has not the rights to read the number it will print out an error message.
   */
#include <stddef.h>
#include <syslog.h>
#include <sys/klog.h>

#define SYSLOG_ACTION_SIZE_UNREAD 9

int main(int argc, char *argv[]) {
	int nbytes;
	syslog(LOG_NOTICE, "My first command from oneshot service started\n");
	nbytes = klogctl(SYSLOG_ACTION_SIZE_UNREAD, NULL, 0);
	if (nbytes >= 0)
		syslog(LOG_NOTICE, "The number of bytes currently available to be read from the kernel log buffer: %d bytes\n", nbytes);
	else
		syslog(LOG_NOTICE, "You do not have the rights to read the number of bytes currently available to be read from the kernel log buffer\n", nbytes);
	// wait for something
	sleep(1);
	return 0;
}
