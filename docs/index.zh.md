# Kai

一款**开源的、具有持久记忆能力的 AI 助手**，支持 **Android、iOS、Windows、Mac、Linux 和 Web** 平台。

[:material-download: 快速开始](getting-started.md){ .md-button .md-button--primary }
[:material-github: GitHub](https://github.com/SimonSchubert/Kai){ .md-button }

## 概述

Kai 基于 Kotlin Multiplatform 和 Compose Multiplatform 构建。它可以连接 11+ 个 LLM 服务商并支持自动故障转移，能够跨对话记住重要信息，还可以通过定时心跳和工具执行实现自主运行。

## 核心功能

- **持久记忆** — Kai 能跨对话记住重要细节，并自动加以利用
- **可定制人格** — 通过可编辑的系统提示词定义 AI 的个性和行为
- **多服务故障转移** — 配置多个服务商，某个失败时自动尝试下一个
- **工具执行** — 网页搜索、通知、日历事件、Shell 命令等
- **自主心跳** — 定期自检，发现需要关注的事项时主动通知
- **加密存储** — 对话数据本地加密存储
- **文字转语音** — 可朗读 AI 的回复
- **图片附件** — 支持在对话中附加图片

## 工作原理

```
                    ┌────────┐
                    │  用户  │
                    └───┬────┘
                        │ 消息
                        ▼
           ┌─────────────────────────┐
           │          对话           │
           │                         │
           │  提示词 + 记忆          │
           │        │                │
           │        ▼                │
           │    ┌────────┐           │
           │    │   AI   │◀─┐        │
           │    └───┬────┘  │        │
           │        │  工具调用      │
           │        │  与返回结果    │
           │        ▼      │        │
           │    ┌────────┐ │        │
           │    │  工具  │─┘        │
           │    └───┬────┘          │
           │        │               │
           └────────┼───────────────┘
                    │ 存储 / 检索
                    ▼
           ┌─────────────────┐    命中次数 >= 5
           │      记忆       │───────────────────┐
           │                 │                   │
           │  事实、偏好、   │                   ▼
           │  学习内容       │          ┌────────────────┐
           │                 │◀─删除───│ 提升至系统     │
           └─────────────────┘          │ 提示词         │
                    ▲                   └────────────────┘
                    │ 审查
                    │
           ┌─────────────────┐
           │      心跳       │
           │                 │
           │  自主自检       │
           │  每 30 分钟     │
           │  (8:00–22:00)   │
           │                 │
           │  一切正常？     │
           │  → 保持静默     │
           │  需要处理？     │
           │  → 通知用户     │
           └─────────────────┘
```

## 支持的服务

| 服务 | API 类型 |
|---|---|
| [OpenAI](https://openai.com) | OpenAI 兼容 |
| [Gemini](https://aistudio.google.com) | Gemini 原生 |
| [DeepSeek](https://www.deepseek.com) | OpenAI 兼容 |
| [Mistral](https://mistral.ai) | OpenAI 兼容 |
| [xAI](https://x.ai) | OpenAI 兼容 |
| [OpenRouter](https://openrouter.ai) | OpenAI 兼容 |
| [Groq](https://groq.com) | OpenAI 兼容 |
| [NVIDIA](https://developer.nvidia.com) | OpenAI 兼容 |
| [Cerebras](https://cerebras.ai) | OpenAI 兼容 |
| [Ollama Cloud](https://ollama.com) | OpenAI 兼容 |
| OpenAI 兼容 API（Ollama、LM Studio 等） | OpenAI 兼容 |

另外还有内置的**免费**层级，无需 API 密钥即可使用。

## 支持平台

| 平台 | 分发方式 |
|---|---|
| Android | Google Play、F-Droid、APK |
| iOS | App Store |
| macOS | Homebrew、DMG |
| Windows | MSI |
| Linux | DEB、RPM、AppImage、AUR |
| Web | 浏览器 |

## 功能文档

- **[对话与会话](features/chat.md)** — 消息历史、对话持久化、图片附件和语音输出
- **[多服务](features/multi-service.md)** — 服务商配置、故障转移链和连接验证
- **[工具](features/tools.md)** — 可用工具、执行流程、安全防护和启用方式
- **[记忆](features/memories.md)** — 记忆生命周期、分类、强化和提升机制
- **[心跳](features/heartbeat.md)** — 自主自检、活跃时段和配置
- **[任务](features/tasks.md)** — 定时任务、延迟执行和任务管理
- **[守护进程](features/daemon.md)** — 用于执行定时任务和心跳的后台服务

## 链接

- [GitHub 仓库](https://github.com/SimonSchubert/Kai)
- [问题追踪](https://github.com/SimonSchubert/Kai/issues)
- [版本发布](https://github.com/SimonSchubert/Kai/releases)
- [Web 应用](https://simonschubert.github.io/Kai)
