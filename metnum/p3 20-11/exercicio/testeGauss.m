 %4.1 gauss
 A = [4 -2 -3 6;-6 7 6.5 -6;1 7.5 6.25 5.5;-12 22 15.5 -1];
 B = [12;-6.5;16;17];
 sola = gauss(A,B)
 C = [9 -4 -2 0;-4 17 -6 -3;-2 -6 14 -6;0 -3 -6 11];
 D = [24;-16;0;18];
 solb = gauss(C,D)
 
 %4.2 gaussPivot
 a = [0 0.9231 0 0 0 0 0 0;-1 -0.3846 0 0 0 0 0 0; 0 0 0 0 1 0 0.8575 0; 1 0 -0.7809 0 0 0 0 0;0 -0.3846 -0.7809 0 -1 0.3846 0 0; 0 0.9231 0.6247 0 0 -0.9231 0 0;0 0 0.6247 -1 0 0 0 0; 0 0 0 1 0 0 -0.5145 -1];
 b = [1690;3625;0;0;0;0;0;0];
 Forcas = gaussPivot(a,b)