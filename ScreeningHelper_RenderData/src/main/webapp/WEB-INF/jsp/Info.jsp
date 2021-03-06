<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<body>

<div class="container">
    <div>
        <table>
            <thead>
            <tr>
                <th>#</th>
                <th>PYKRX INFO</th> </tr>
            </thead>
            <tr>
                <th>종목코드</th>
                <th>종목명</th>
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