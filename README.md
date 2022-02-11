* You have to write a PriceThrottler class which will implement the following requirements:
*
    1) Implement PriceProcessor interface
*
    2) Distribute updates to its listeners which are added through subscribe() and
* removed through unsubscribe()
*
    3) Some subscribers are very fast (i.e. onPrice() for them will only take a microsecond) and some are very slow
* (onPrice() might take 30 minutes). Imagine that some subscribers might be showing a price on a screen and some
* might be printing them on a paper
*
    4) Some ccyPairs change rates 100 times a second and some only once or two times a day
*
    5) ONLY LAST PRICE for each ccyPair matters for subscribers. I.e. if a slow subscriber is not coping
* with updates for EURUSD - it is only important to deliver the latest rate
*
    6) It is important not to miss rarely changing prices. I.e. it is important to deliver EURRUB if it ticks once
* per day but you may skip some EURUSD ticking every second
*
    7) You don't know in advance which ccyPair are frequent and which are rare. Some might become more frequent
* at different time of a day
*
    8) You don't know in advance which of the subscribers are slow and which are fast.
*
    9) Slow subscribers should not impact fast subscribers
*
* In short words the purpose of PriceThrottler is to solve for slow consumers

######  ####################

* Вы должны написать класс PriceThrottler, который будет реализовывать следующие требования:
*
    1) Реализовать интерфейс PriceProcessor
*
    2) Рассылать обновления своим слушателям, которые добавляются через subscribe() и
* удалено через отписку()
*
    3) Некоторые подписчики очень быстрые (т.е. onPrice() для них займет всего микросекунду), а некоторые очень
       медленные
* (onPrice() может занять 30 минут). Представьте, что некоторые подписчики могут показывать цену на экране, а некоторые
* может распечатать их на бумаге
*
    4) Некоторые ccyPair меняют курс 100 раз в секунду, а некоторые только один или два раза в день.
*
    5) Для абонентов имеет значение ТОЛЬКО ПОСЛЕДНЯЯ ЦЕНА за каждую ccyPair. т.е. если медленный абонент не справляется
* с обновлениями для EURUSD - важно только поставить последний курс
*
    6) Важно не пропустить редко меняющиеся цены. т.е. важно доставить EURRUB, если он тикает один раз
* в день, но вы можете пропустить несколько тиков EURUSD каждую секунду
*
    7) Вы не знаете заранее, какие ccyPair являются частыми, а какие редкими. Некоторые могут стать более частыми
* в разное время суток
*
    8) Вы не знаете заранее, какие из абонентов медленные, а какие быстрые.
*
    9) Медленные подписчики не должны влиять на быстрых подписчиков

/**

* Вызов из восходящего потока
*
* Вы можете считать, что сюда поступают только корректные данные - дополнительная проверка не требуется
*
* @param ccyPair - EURUSD, EURRUB, USDJPY - до 200 различных валютных пар
* @param rate - любая двойная скорость, например 1.12, 200.23 и т. д.
  */

/**

* Подпишитесь на обновления
*
* Вызывается редко во время работы PriceProcessor
*
* @param priceProcessor - может быть до 200 подписчиков
  */

/**

* Отписаться от обновлений
*
* Вызывается редко во время работы PriceProcessor
*
* @param ценаПроцессор
  */