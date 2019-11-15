function r = splineQuadratica(x, y, value)
N   =   length(x)-1;
V = zeros(N*3, 1);
Z   =   zeros(length(V),length(V));
j=1;
f=1;
for i=2:2:2*N    
    Z(i,f:f+2) = [x(j)^2 x(j) 1];
    V(i) = y(j);
    
    j = j+1;
    Z(i+1,f:f+2) = [x(j)^2 x(j) 1];  
    V(i+1) = y(j);
    f = f+3;
end

j=1;
l=2;
for i=2*N+2:3*N
    
    Z(i,j:j+1) = [2*x(l) 1];
    Z(i,j+3:j+4) = [-2*x(l) -1];
    j = j+3;
    l = l+1;
end
% Adjusting the value of a1 to be zero "Linear Spline"
Z(1,1)=1;
% Inverting and obtaining the coeffiecients, Plotting
Coeff = Z\V;

for j = 1 : N
    if (value < y(j))
        j = (j+1)*2;
        break
    end
    
end
r = Coeff(j)*value^2 + Coeff(j+1)*value + Coeff(j+2);

end
