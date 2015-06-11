/**
 * Created by wzw on 2014/9/1.
 * all search post you konw so i can't prevent so it happened
 */
function tabSearch(form,tabId) {
    var $form = $(form);
    var _pageNo = $form.find(':hidden[name="pageNo"]');
    $(tabId, 'body').ajaxUrl({url: $form.attr('action'), data: $form.serializeArray()});
    return false;
}

function listSearch(form,targetType) {
    if(targetType == 'dialog') return dialogSearch(form);
    return tabSearch(form, '.mainbar');
}

function dialogSearch(form) {
    var $form = $(form);
    console.info($form.serializeArray());
    $.mzdialog.reload($form.attr('action'), {data: $form.serializeArray()});
    return false;
}