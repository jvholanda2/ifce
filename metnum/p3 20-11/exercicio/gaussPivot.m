%4.2
function x = gaussPivot(a, b)
%A fun��o resolve um sistema de equa��es lineares [a][x]=[b] usando o
%m�todo de elimina��o de Gauss com pivota��o.
%Vari�veis de entrada:
%Matriz de coeficientes.
%Vetor coluna contendo as constantes do lado direito do sistema.
%Vari�vel de sa�da:
%Vetor coluna com a solu��o.
ab = [a, b];
[R, C] = size(ab);
for j = 1:R-1  
    %Come�a a se��o de pivota��o
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
    %Termina a se��o de pivota��o
    for i = j+1: R
        ab(i,j:C) = ab(i,j:C) - ab(i,j)/ab(j,j)*ab(j,j:C);
    end
end

x = zeros(R,1);
x(R) = ab(R,C)/ab(R,R);

for i = R-1 :-1 :1
    x(i) = (ab(i, C) - ab(i, i+1: R) * x(i+1:R)) / ab(i, i);
end