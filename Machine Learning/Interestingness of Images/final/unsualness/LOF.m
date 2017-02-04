function results = LOF(image, k)

    N = size(image, 2);
    
    kdistances = zeros(N, 1);
    kneighbors = zeros(N, k);
    dist = zeros(N, N);
    sdist = zeros(N, N);    
    
    % step 1: calculate all the distances between each two data points
    for i = 1:N
        % find k-distance and neighbors of sample
        tmp = repmat(double(image(1, i)), 1, N);        
        tmp = double(image) - tmp;
        tmp = abs(tmp);
        dist(i, :) = tmp;
        dist(i, i) = max(dist(i, :));        
        sdist(i, :) = sort(dist(i, :));
    end
    
    %step 2: calc all dist_k(O)
    for i = 1:N
        kdistances(i) = sdist(i, k);
    end
    clear sdist;    

    % step 3: calculate all k-distance neighborhood of o
    for i = 1:N
        tmp = find(dist(i, :) <= kdistances(i));        
        
        % disp((tmp));
        kneighbors(i, :) = tmp(1:k);
    end
    
    % step 4: calculate all the lrd_k(o)
    for i = 1:N
        nCount = size(kneighbors(i), 1);
        for j = 1:k
            b = kneighbors(i, j);
            rd(j) = max(kdistances(b), dist(b, i));
        end
        lrd(i) = nCount ./ (1 + sum(rd));%fixed zeros
    end    
    % step 5: calculate all the LOF_k(o)
    for i = 1:N
        sumlrd = 0;
        sumrd = 0;
        for j = 1:k
            sumlrd = sumlrd + lrd(j);
            b = kneighbors(i, j);
            sumrd = sumrd + max(kdistances(b), dist(b, i));
        end
        lof(i) = sumlrd*sumrd;
        if isnan(lof(i)) || isinf(lof(i))
           lof(i) = 0;
        
    end
    results = max(lof);
    end
