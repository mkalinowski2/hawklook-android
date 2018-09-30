package com.fewbits.hawklook.detektplugin

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class RulesProvider(override val ruleSetId: String = "custom") : RuleSetProvider {

  override fun instance(config: Config): RuleSet {
    return RuleSet(
      id = ruleSetId,
      rules = listOf(
        ExtendedWildcardImport(config)
      )
    )
  }
}
