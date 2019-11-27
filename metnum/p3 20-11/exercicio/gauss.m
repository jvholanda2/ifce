%4.1

function x = Gauss(a,b)
%A função resolve um sistema de equações lineares [a][x]=[b] usando o
%método de eliminação de Gauss.
%Variáveis de entrada:
%a Matriz de coeficientes.
%b Vetor coluna contendo as constantes do lado direito do sistema.
%Variável de saída:
%x Vetor coluna com a solução.

ab = [a,b];
[R,C] = size(ab);
for j = 1:R -1
    for i = j + 1:R 
        ab(i,j:C) = ab(i,j:C) - ab(i,j)/ab(j,j)*ab(j,j:C);
    end 
end
x = zeros(R,1);
x(R) =ab(R,C)/ab(R,R)
for i = R - 1:-1:1
    x(i)=(ab(i,C) - ab(i,i+1:R)*x(i+1:R))/ab(i,i)
end