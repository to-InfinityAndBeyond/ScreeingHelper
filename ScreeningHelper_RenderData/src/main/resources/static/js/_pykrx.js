function inputData(){
    var book = $('#form').serialize();
    $.ajax({
        url: "/test_1",
        data: book,
        type:"POST",
        cache: false
    }).done(function (fragment) {
         $("#list").replaceWith(fragment);
    });

}