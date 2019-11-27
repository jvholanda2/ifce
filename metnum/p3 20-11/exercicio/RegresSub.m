%4.5
function y = RegresSub(a, b)
%A função resolve um sistema de equações lineares ax = b, onde a é uma
%matriz triangular superior, usando a substituição regressiva.
%Variáveis de entrada:
%a Matriz de coeficientes.
%b Vetor coluna de constantes.
%Variável de saída:
%y Vetor coluna com a solução.

n = length(b);
y = zeros(length(b), 1);
y(n, 1) = b(n)/a(n,n);
for i = n-1: -1:1
    y(i, 1) = (b(i)- a(i, i+1:n)* y(i+1:n,1))./a(i,i);
end