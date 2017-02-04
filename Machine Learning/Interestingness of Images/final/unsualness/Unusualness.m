function [sUnusualPixel, sUnusualGist, sUnusualPyr] = calcUnu(image)
    im = image;
    sUnusualPixel = UnuPixel(im);
    sUnusualGist = UnuGIST(im);
    sUnusualPyr = UnuPyr(im);