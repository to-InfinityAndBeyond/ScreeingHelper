<!DOCTYPE html>
<html>
<head>
    <title>Stock</title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>
    <script type="text/javascript">
        function ajaxUpdate(){
            $.ajax({
                typ,
                url : "/Info/update",
                dataType : "post",
                success : function(data) {
                  alert(data);
                  $('#list').replaceWith(data);
                },
                error : function(data) {
                  alert(data);
                  clearInterval(playTimer);
                }
          });
        }
        $(document).ready(function(){
            playTimer = setInterval(function() {
                ajaxUpdate();
            }, 1000);
        });
    </script>
</head>
<body>
    <div class="container">
        <div id="stockList">
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>PYKRX INFO</th> </tr>
                </thead>
                <tr>
                    <th>Code</th>
                    <th>Name</th>
                    <th>BPS</th>
                    <th>PER</th>
                    <th>PBR</th>
                    <th>EPS</th>
                    <th>DIV</th>
                    <th>DPS</th>
                    <th>ROE</th>
                </tr>
                <tbody>
                <tr th:each="pykrxInfo : ${pykrxInfo}">
                    <td th:text="${pykrxInfo.id}"></td>
                    <td th:text="${pykrxInfo.name}"></td>
                    <td th:text="${pykrxInfo.BPS}"></td>
                    <td th:text="${pykrxInfo.PER}"></td>
                    <td th:text="${pykrxInfo.PBR}"></td>
                    <td th:text="${pykrxInfo.EPS}"></td>
                    <td th:text="${pykrxInfo.DIV}"></td>
                    <td th:text="${pykrxInfo.DPS}"></td>
                    <td th:text="${pykrxInfo.ROE}"></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div> <!-- /container -->
</body>
</html>

