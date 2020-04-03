var fun = function () {
    var i = 0;
    try {
        i = 1;
        return i;
    } catch (e) {
        i = 2;
        return i;

    } finally {
        i = 3;
    }
}

console.log(fun());
