%4.4
function y = ProgresSub(a, b)
%A fun��o resolve um sistema de equa��es lineares ax = b, onde a � um
%matriz triangular inferior, usando a substitui��o progressiva.
%Vari�veis de entrada:
%a Matriz de coeficientes.
%b Vetor coluna de constantes.
%Vari�vel de sa�da:
% yVetor coluna com a solu��o.

n = length(b);
y = zeros(length(b), 1);
y(1, 1) = b(1)/a(1,1);
for i = 2: n
    y(i, 1) = (b(i)- a(i, 1: i-1)* y(1: i-1,1))./a(i,i);
end