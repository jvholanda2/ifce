%4.5
L = [4 0 0 0;-6, 4 0 0;1 8 3 0;-12 16 -1.5 4];
U = [1 -0.5 -0.75 1.5;0 1 0.5 0.75;0 0 1 -0.6667;0 0 0 1];
L*U

% 4.6
A = [9 -4 -2 0;-4 17 -6 -3;-2 -6 14 -6;0 -3 -6 11];
b = [24;-16;0;18];
[L, U] = LUdecompCrount(A);%4.3
y = ProgresSub(L, b);
i = RegresSub(U, y)
