toastr.options = {
    closeButton: true,
    debug: false,
    newestOnTop: false,
    progressBar: true,
    positionClass: "toast-bottom-right",
    preventDuplicates: false,
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    timeOut: "3000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut"
};

function parseMsg(msg) {
    return msg.split(";ttl=");
}

function toastWarning(msg){
    const [_msg, ttl] = parseMsg(msg);

    if (ttl && parseInt(ttl) < new Date().getTime()) return;

    toastr["warning"](_msg, "");
}

function toastNotice(msg){
    const [_msg, ttl] = parseMsg(msg);

    if (ttl && parseInt(ttl) < new Date().getTime()) return;

    toastr["success"](_msg, "");
}

function getQueryParams() {
    const params = new URLSearchParams(window.location.search);
    const paramsObj = {};

    for (const [key, value] of params) {
        paramsObj[key] = value;
    }

    return paramsObj;
}