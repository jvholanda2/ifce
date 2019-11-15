%4.2
function x = gaussPivot(a, b)
%A função resolve um sistema de equações lineares [a][x]=[b] usando o
%método de eliminação de Gauss com pivotação.
%Variáveis de entrada:
%Matriz de coeficientes.
%Vetor coluna contendo as constantes do lado direito do sistema.
%Variável de saída:
%Vetor coluna com a solução.
ab = [a, b];
[R, C] = size(ab);
for j = 1:R-1  
    %Começa a seção de pivotação
     if ab(j,j) == 0
        for k = j+1: R
            if ab(k,j) ~= 0
                abTemp = ab(j,:);
                ab(j,:) = ab(k,:);
                ab(k,:) = abTemp;
                break
            end
        end
    end
    %Termina a seção de pivotação
    for i = j+1: R
        ab(i,j:C) = ab(i,j:C) - ab(i,j)/ab(j,j)*ab(j,j:C);
    end
end

x = zeros(R,1);
x(R) = ab(R,C)/ab(R,R);

for i = R-1 :-1 :1
    x(i) = (ab(i, C) - ab(i, i+1: R) * x(i+1:R)) / ab(i, i);
end