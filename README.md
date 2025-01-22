# 💈 NowBarber - App de Barbearias e Serviços para Cabelo e Barba

O **NowBarber** é um aplicativo desenvolvido em **Kotlin** com **Jetpack Compose**, projetado para facilitar o agendamento de serviços em barbearias. Ele utiliza o modelo **Offline First**, garantindo que os usuários possam acessar e gerenciar informações mesmo sem conexão com a internet.

---

## 📋 Funcionalidades

- **Agendamento de Serviços**: Permite que clientes agendem serviços como cortes de cabelo e barba diretamente pelo app.  
- **Catálogo de Serviços**: Exibição dos serviços disponíveis com descrições e preços.  
- **Perfil do Profissional**: Informações detalhadas sobre os barbeiros, incluindo avaliações e especialidades.  
- **Histórico de Agendamentos**: Acompanhamento de serviços realizados e futuros.  
- **Notificações**: Lembretes automáticos para agendamentos e promoções.  
- **Sincronização Offline**: Usuários podem acessar dados localmente e sincronizar alterações com o servidor quando estiverem online.  

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Kotlin  
- **UI**: Jetpack Compose  
- **Banco de Dados Local**: Room (armazenamento local para suporte ao modelo Offline First)  
- **Backend**: Firebase (armazenamento remoto e sincronização de dados)  
- **Arquitetura**: MVVM (Model-View-ViewModel)  
- **Gerenciamento de Dependências**: Gradle  

---

## 🚀 Modelo Offline First

O **NowBarber** foi projetado para funcionar em ambientes com conectividade limitada, utilizando:

- **Room**: Para armazenar dados localmente no dispositivo, permitindo acesso rápido e offline.  
- **Firebase**: Para sincronizar dados com o servidor remoto quando a conexão é restabelecida.  
- **Sincronização Automática**: Alterações realizadas offline são sincronizadas com o Firebase, garantindo consistência de dados entre dispositivos.  

---

## 🚀 Benefícios

- **Acessibilidade Offline**: Usuários podem acessar e gerenciar informações sem conexão à internet.  
- **Experiência Moderna**: Interface fluida e responsiva com Jetpack Compose.  
- **Eficiência**: Agendamentos rápidos e gerenciamento centralizado.  
- **Flexibilidade**: Fácil adaptação para diferentes barbearias e serviços.  
- **Escalabilidade**: Pronto para integração com novas funcionalidades e expansão de negócios.  

---

## 📄 Licença

Este projeto está disponível sob a licença [MIT](LICENSE).

---

## 🧑‍💻 Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e enviar pull requests.

---

## 📞 Contato

- **Desenvolvedor:** Jhonny Guimarães  
- **E-mail:** guimaraes.jhonny@outlook.com  
- **LinkedIn:** [Jhonny Guimarães](http://linkedin.com/in/jhonny-guimaraes)  
- **GitHub:** [codeguima](https://github.com/codeguima)
