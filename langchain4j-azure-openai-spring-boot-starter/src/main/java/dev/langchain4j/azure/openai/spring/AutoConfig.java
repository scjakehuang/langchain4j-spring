package dev.langchain4j.azure.openai.spring;

import com.azure.core.http.ProxyOptions;
import com.azure.core.util.Configuration;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.azure.AzureOpenAiChatModel;
import dev.langchain4j.model.azure.AzureOpenAiEmbeddingModel;
import dev.langchain4j.model.azure.AzureOpenAiImageModel;
import dev.langchain4j.model.azure.AzureOpenAiStreamingChatModel;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

import static dev.langchain4j.azure.openai.spring.Properties.PREFIX;

@AutoConfiguration
@EnableConfigurationProperties(Properties.class)
public class AutoConfig {

    @Bean
    @ConditionalOnProperty(PREFIX + ".chat-model.deployment-name")
    @ConfigurationProperties
    AzureOpenAiChatModel openAiChatModel(Properties properties) {
        ChatModelProperties chatModelProperties = properties.getChatModel();
        AzureOpenAiChatModel.Builder builder = AzureOpenAiChatModel.builder()
                .endpoint(chatModelProperties.getEndpoint())
                .apiKey(chatModelProperties.getApiKey())
                .deploymentName(chatModelProperties.getDeploymentName())
                .temperature(chatModelProperties.getTemperature())
                .topP(chatModelProperties.getTopP())
                .maxTokens(chatModelProperties.getMaxTokens())
                .presencePenalty(chatModelProperties.getPresencePenalty())
                .frequencyPenalty(chatModelProperties.getFrequencyPenalty())
                .timeout(Duration.ofSeconds(chatModelProperties.getTimeout()))
                .maxRetries(chatModelProperties.getMaxRetries())
                .proxyOptions(ProxyOptions.fromConfiguration(Configuration.getGlobalConfiguration()))
                .logRequestsAndResponses(chatModelProperties.getLogRequestsAndResponses() != null && chatModelProperties.getLogRequestsAndResponses());
        if (chatModelProperties.getNonAzureApiKey() != null) {
            builder.nonAzureApiKey(chatModelProperties.getNonAzureApiKey());
        }
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(PREFIX + ".streaming-chat-model.deployment-name")
    AzureOpenAiStreamingChatModel openAiStreamingChatModel(Properties properties) {
        ChatModelProperties chatModelProperties = properties.getStreamingChatModel();
        AzureOpenAiStreamingChatModel.Builder builder= AzureOpenAiStreamingChatModel.builder()
                .endpoint(chatModelProperties.getEndpoint())
                .apiKey(chatModelProperties.getApiKey())
                .deploymentName(chatModelProperties.getDeploymentName())
                .temperature(chatModelProperties.getTemperature())
                .topP(chatModelProperties.getTopP())
                .stop(chatModelProperties.getStop())
                .maxTokens(chatModelProperties.getMaxTokens())
                .presencePenalty(chatModelProperties.getPresencePenalty())
                .frequencyPenalty(chatModelProperties.getFrequencyPenalty())
                .timeout(Duration.ofSeconds(chatModelProperties.getTimeout()))
                .proxyOptions(ProxyOptions.fromConfiguration(Configuration.getGlobalConfiguration()))
                .logRequestsAndResponses(chatModelProperties.getLogRequestsAndResponses() != null && chatModelProperties.getLogRequestsAndResponses());
        if (chatModelProperties.getNonAzureApiKey() != null) {
            builder.nonAzureApiKey(chatModelProperties.getNonAzureApiKey());
        }
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty({PREFIX + ".embedding-model.deployment-name"})
    @ConditionalOnBean(Tokenizer.class)
    AzureOpenAiEmbeddingModel openAiEmbeddingModel(Properties properties, Tokenizer tokenizer) {
        EmbeddingModelProperties embeddingModelProperties = properties.getEmbeddingModel();
        AzureOpenAiEmbeddingModel.Builder builder= AzureOpenAiEmbeddingModel.builder()
                .endpoint(embeddingModelProperties.getEndpoint())
                .apiKey(embeddingModelProperties.getApiKey())
                .deploymentName(embeddingModelProperties.getDeploymentName())
                .maxRetries(embeddingModelProperties.getMaxRetries())
                .tokenizer(tokenizer)
                .proxyOptions(ProxyOptions.fromConfiguration(Configuration.getGlobalConfiguration()))
                .logRequestsAndResponses(embeddingModelProperties.getLogRequestsAndResponses() != null && embeddingModelProperties.getLogRequestsAndResponses());

        if (embeddingModelProperties.getNonAzureApiKey() != null) {
            builder.nonAzureApiKey(embeddingModelProperties.getNonAzureApiKey());
        }
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(PREFIX + ".image-model.deployment-name")
    AzureOpenAiImageModel openAiImageModel(Properties properties) {
        ImageModelProperties imageModelProperties = properties.getImageModel();
        AzureOpenAiImageModel.Builder builder= AzureOpenAiImageModel.builder()
                .endpoint(imageModelProperties.getEndpoint())
                .apiKey(imageModelProperties.getApiKey())
                .deploymentName(imageModelProperties.getDeploymentName())
                .size(imageModelProperties.getSize())
                .quality(imageModelProperties.getQuality())
                .style(imageModelProperties.getStyle())
                .user(imageModelProperties.getUser())
                .responseFormat(imageModelProperties.getResponseFormat())
                .timeout(Duration.ofSeconds(imageModelProperties.getTimeout()))
                .maxRetries(imageModelProperties.getMaxRetries())
                .proxyOptions(ProxyOptions.fromConfiguration(Configuration.getGlobalConfiguration()))
                .logRequestsAndResponses(imageModelProperties.getLogRequestsAndResponses() != null && imageModelProperties.getLogRequestsAndResponses());
        if (imageModelProperties.getNonAzureApiKey() != null) {
            builder.nonAzureApiKey(imageModelProperties.getNonAzureApiKey());
        }
        return builder.build();
    }
}