package ${PACKAGE_NAME}.detektplugin

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtImportDirective

class ExtendedWildcardImport(config: Config = Config.empty) : Rule(config) {

  private val IMPORT_EXCEPTIONS_REGEX = "^\\\\[()]\\$|^\\\\[((?:\\\\\\"\\\\S+\\\\.\\\\*\\\\\\",\\\\s?)*(?:\\\\\\"\\\\S+\\\\.\\\\*\\\\\"))\\\\]\\$"

  private val IMPORT_EXCEPTIONS = "importExceptions"

  override val issue = Issue(
    id = javaClass.simpleName,
    severity = Severity.Style,
    description = "ExtendedWildcardImport",
    debt = Debt(mins = 1)
  )

  private val importExceptions = valueOrDefault(IMPORT_EXCEPTIONS, "[]")

  private val parsedImportExceptions by lazy {
    val regex = IMPORT_EXCEPTIONS_REGEX.toRegex()
    val groups = regex.matchEntire(importExceptions) ?: error("Incorrect wildcard rule detected.")

    val values = groups.groupValues
      .drop(1)
      .filterNot { it.isEmpty() }

    val rules = values.firstOrNull()
      ?.filterNot { it.isWhitespace() }
      ?.split(',')
      ?.map { it.drop(1).dropLast(1) } /* removing quote `"` characters that wrap imports */
      ?.map { it.dropLast(1) } /* removing `*` character */

    return@lazy rules ?: emptyList<String>()
  }

  override fun visitImportDirective(importDirective: KtImportDirective) {
    super.visitImportDirective(importDirective)
    val import = importDirective.importPath?.pathStr
    import?.takeUnless(this::isValidImport)?.run {
      report(CodeSmell(issue, Entity.from(importDirective), "Wildcard imports allowed only for exceptions " +
        "listed in config file"))
    }
  }

  private fun isValidImport(importPath: String): Boolean {
    if ("*" !in importPath) {
      return true
    }
    return parsedImportExceptions.count { importPath.startsWith(it) } > 0
  }
}
