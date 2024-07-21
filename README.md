# budget-buddy

Sistema pessoal para rastreamento financeiro e consolidação de conhecimentos

---

## Planejamento

#### 1. Modelagem de Dados

**Usuários:** Informações básicas (nome, e-mail, senha).
**Categorias de Despesas:** Diferentes tipos de despesas (alimentação, transporte, lazer...).
**Despesas:** Valor, data, descrição, categoria, e usuário associado.
**Receitas:** Valor, data, descrição, categoria, e usuário associado.
**Orçamento:** Valores planejados para cada categoria de despesa.
**Metas de Economia:** Valores alvo de economia em determinados períodos.

#### 2. Funcionalidades Principais

**Cadastro de Usuário:** Sistema de registro e autenticação de usuários.
**Registro de Despesas e Receitas:** Funcionalidade para o usuário adicionar, editar e excluir despesas e receitas.
**Categorias Personalizadas:** Permitir que o usuário crie e gerencie suas próprias categorias de despesas e receitas.
**Dashboard de Resumo:** Visão geral das finanças, mostrando total de despesas, receitas, saldo, e gráficos.
**Relatórios e Gráficos:** Relatórios detalhados e gráficos que mostram onde o dinheiro está sendo gasto.
**Alertas e Notificações:** Notificações para quando o usuário estiver próximo de exceder seu orçamento em uma categoria específica.
**Exportação de Dados:** Opção para exportar dados financeiros em formatos como CSV ou PDF.

#### 3. Regras de Negócio

**Validação de Dados:** Garantir que todos os dados inseridos sejam válidos (ex.: valores numéricos, datas válidas).
**Cálculo de Saldo:** Cálculo automático do saldo do usuário com base nas receitas e despesas.
**Categorização Automática:** Utilizar regras ou algoritmos para categorizar despesas automaticamente com base em descrições comuns.
**Orçamento:** Comparar despesas reais com o orçamento planejado e calcular variações.
**Análise de Tendências:** Analisar padrões de gasto ao longo do tempo para identificar tendências e sugerir economias.

---