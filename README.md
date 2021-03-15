# ScreeingHelper
Helper for Screening stocks that have been underrated but expected. The base data from [PYKRX](https://github.com/sharebook-kr/pykrx)

# 1. Introduction
## 1.1 Purpose  
주식의 '주'도 모르는 주린이 [윤서](https://github.com/jungAcat).  
요즘 안하는 사람이 없는 **주식**을 하고 싶지만, 아는 것이 없어 일개 [개미](https://namu.wiki/w/%EA%B0%9C%EB%AF%B8(%EC%A3%BC%EC%8B%9D))가 되기 일보직전..!!  
따라서, 주식을 잘 아는 [광민](https://github.com/codingple) 의 도움으로 함께 주식 지수를 알려주면서 유망지수 순위를 계산해주는 서비스를 개발하고자 한다.  
<br>

## 1.2 Overview  

>개미 탈출의 기본은 **분석**  
  
  
주식 관련 정보엔 기업을 분석할 수 있는 다양한 지표들이 존재한다.  
<br>
[PYKRX](https://github.com/sharebook-kr/pykrx) 로부터 PER / BPS / BER / ...  
[OpenDART](https://opendart.fss.or.kr/) 로부터 재무재표 등의 공시정보 ...  
  
모든 정보들은 주식을 위해 기업을 공부하는데 있어서 도움이 된다.  
우리는 여기에 몇 중요 지수들을 사용자가 입력한 **중요도** 에 맞춰 주식정보를 Ordering 하는 서비스를 구축하고자 한다.  
<br>

# 2. Description  
## 2.1 Stock Info  
### 2.1.1 PYKRX  
[PYKRX](https://github.com/sharebook-kr/pykrx) 를 통해 시장의 주식정보를 가져온다.  
아래는 가져오는 정보들이다  
<br>
|Name|Description|ex|
|:----:|:----------:|:---:|
|종목코드|6 자리의 주식 고유코드||
|BPS|주당순자산가치||
|PER|주가수익률||
|PBR|주가순자산비율||
|EPS|주당순이익||
|DIV|배당수익률||
|DPS|주당배당금||
|ROE|자기자본이익률||
|curPrice|시가 : 금일 장 시작 주가 <br>(전일종가)||
|highPrice|고가 : 금일 최고 주가||
|lowPrice|저가 : 금일 최저 주가||
|endPrice|종가 : 금일 마감 주가 <br>(당일 기준 현재가)||
|Volume|거래량||
|Transaction|거래대금||
|Fluctuation|등락률||  

<br>

### 2.1.2 OpenDART  
[OpenDART](https://opendart.fss.or.kr/) 를 통해 기업의 공시정보를 가져온다.  
아래는 가져오는 정보들이다.

<br>

|Name|Description|ex|
|:----:|:----------:|:---:|



