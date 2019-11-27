%4.4
function y = ProgresSub(a, b)
%A função resolve um sistema de equações lineares ax = b, onde a é um
%matriz triangular inferior, usando a substituição progressiva.
%Variáveis de entrada:
%a Matriz de coeficientes.
%b Vetor coluna de constantes.
%Variável de saída:
% yVetor coluna com a solução.

n = length(b);
y = zeros(length(b), 1);
y(1, 1) = b(1)/a(1,1);
for i = 2: n
    y(i, 1) = (b(i)- a(i, 1: i-1)* y(1: i-1,1))./a(i,i);
end