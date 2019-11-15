function [y] = gaussJordan (a, b)

    a = [a b]
    for i = 1:(length(a)-1)
        A = a(i,:);
        pivotX = A/A(i);
        a(i,:) = pivotX;
     
        for j = 1 : (length(a)-1)
            if i~=j
                a(j, :) = -a(j, i)*pivotX + a(j, :);
            end
        end
    end
    a
    y = a(:, size(a,2))
end