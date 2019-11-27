%4.5
function y = RegresSub(a, b)
%A fun��o resolve um sistema de equa��es lineares ax = b, onde a � uma
%matriz triangular superior, usando a substitui��o regressiva.
%Vari�veis de entrada:
%a Matriz de coeficientes.
%b Vetor coluna de constantes.
%Vari�vel de sa�da:
%y Vetor coluna com a solu��o.

n = length(b);
y = zeros(length(b), 1);
y(n, 1) = b(n)/a(n,n);
for i = n-1: -1:1
    y(i, 1) = (b(i)- a(i, i+1:n)* y(i+1:n,1))./a(i,i);
end