<!DOCTYPE html>
<html>
<head>
    <title>Stock</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#save").click(function() {
                //배열 선언
                var fruitArray = [];

                $('input[name="fruit"]:checked').each(function(i){//체크된 리스트 저장
                    fruitArray.push($(this).val());
                });

                var objParams = {
                        "user"      : $("#user").val(), //유저 저장
                        "fruitList" : fruitArray        //과일배열 저장
                    };

                //ajax 호출
                $.ajax({
                    url         :   "/checkTest/save",
                    dataType    :   "json",
                    contentType :   "application/x-www-form-urlencoded; charset=UTF-8",
                    type        :   "post",
                    data        :   objParams,
                    success     :   function(retVal){

                        if(retVal.code == "OK") {
                            alert(retVal.message);
                        } else {
                            alert(retVal.message);
                        }

                    },
                    error       :   function(request, status, error){
                        console.log("AJAX_ERROR");
                    }
                });

            })

        });
    </script>
</head>
<body>
    <table border="1">
        <tr>
            <td>
                <input type="checkbox" name="fruit" value="apple">
            </td>
            <td>
                apple
            </td>
        </tr>
        <tr>
            <td>
                <input type="checkbox" name="fruit" value="banana">
            </td>
            <td>
                banana
            </td>
        </tr>
        <tr>
            <td>
                <input type="checkbox" name="fruit" value="peach">
            </td>
            <td>
                peach
            </td>
        </tr>
    </table>
    <br>
    <input type="text" id="user" value="huskdoll">
    <button type="button" id="save">SAVE</button>
</body>
</html>

