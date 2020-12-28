#! /bin/bash
### BEGIN INIT INFO
# Provides:          gpio_init.sh
# Required-Start:    $remote_fs $syslog
# Required-Stop:     $remote_fs $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Start daemon at boot time
# Description:       Setup GPIO pins for default start up
### END INIT INFO
for a in `seq 0 1`;
do
  /usr/bin/gpio mode $a out;
  /usr/bin/gpio write $a 0;
done
for i in `seq 2 8`;
do
  /usr/bin/gpio mode $i out;
  /usr/bin/gpio write $i 1;
done
for b in `seq 21 22`;
do
  /usr/bin/gpio mode $b out;
  /usr/bin/gpio write $b 0;
done
for j in `seq 23 28`
do
  /usr/bin/gpio mode $j out;
  /usr/bin/gpio write $j 1;
done
