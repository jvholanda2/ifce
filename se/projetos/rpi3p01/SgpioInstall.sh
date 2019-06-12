#!/bin/bash
# Created site: http://sanusb.org/gpio/ivan
# 08/06/18 10:21:55. Tutorial: youtu.be/aMXlZb_IyCo
profile=ivan

# install Wpi
    if [ ! -f "/usr/include/wiringPi.h" ]; then
 	  apt-get install wiringpi
    fi
	
# install sanusb (sanusb.org)
   if [ ! -f "/usr/share/sanusb/sanusb" ]; then
     cd /home/share
     wget http://sanusb.org/tools/SanUSBrpi.zip
	 unzip SanUSBrpi.zip
	 cd SanUSBrpi
	 sudo dpkg -i sanusb_raspberry.deb
	 chmod +x sanusb
	 cp sanusb /usr/share/sanusb
   fi
	
##***********************************************************************************************  
contini=$(curl -sl sanusb.org/gpio/$profile/view)
sleep 1
echo contini=$contini
##***********************************************************************************************

#Start Loop
while :
do
	#Verifica o índice content
	content=$(curl -sl sanusb.org/gpio/$profile/view)
	#echo content=$content
	sleep 2

if (( "$content" != "$contini" )) ; then 
## && (( "$content" != "" ))
		 ((e=2))
		 cont=$content
		
          for i in {40..1} 
          do
          g=$((e**$i)); if (("$cont" >= "g")); then ((cont=cont-g)); gpio -1 mode $i out; gpio -1 write $i 1; else gpio -1 write $i 0; fi
          done    
	    
		gpio readall
		contini=$(curl -sl sanusb.org/gpio/$profile/view)
		sleep 1
	fi
done
