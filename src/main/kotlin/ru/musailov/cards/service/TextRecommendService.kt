package ru.musailov.cards.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import ru.musailov.cards.dto.CardDto
import ru.musailov.cards.model.*

@Service
class TextRecommendService {

    @Value("\${recommend.iam_token}")
    private lateinit var token: String

    @Value("\${recommend.folder_id}")
    private lateinit var folderId: String

    private val systemText = "Ты профессиональный копирайтер, который пишет яркие и продающие тексты."

    private val defaultClient = RestClient.create()


    private val completionOptions = CompletionOptions(
        false,
        0.5f,
        "2000",
        ReasoningOption("DISABLED")
    )

    val requestTask = "Тебе будет дана информация о клиенте, который хочет заполнить поле \"О себе\" в электронной визитке " +
            " Все исходные данные в тексте использовать не обязательно" +
            " Некоторые данные могут быть пропущены (null)." +
            " Длина текста от 2 до 6 предложений." +
            " Текст должен быть написан от первого лица, быть стильным и продающим, не содержать имени/фамилии/отчества. \n\n"

    fun generateText(cardDto: CardDto): GenerateTextResponse? {

        val companyInfo = cardDto.companyInfo
        val userText = requestTask +
            "Должность/род занятий клиента: ${cardDto.position}; " +
            "Ученая степень/образование: ${cardDto.degree};" +
            " Название компании, в которой клиент работает: ${companyInfo?.companyName};" +
            " Род деятельности компании: ${companyInfo?.businessLine}"

        val request = GenerateTextRequest(
            "gpt://$folderId/yandexgpt",
            completionOptions,
            listOf(
                Message("system", systemText),
                Message("user", userText)
            )
        )

        println(request)
        val result = defaultClient
            .post()
            .uri("https://llm.api.cloud.yandex.net/foundationModels/v1/completion")
            .header("Authorization", "Bearer $token")
            .body(request)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(GenerateTextResponse::class.java)

        println(result)
        return result

    }
}