thymeleaf-extras
================

Thymeleaf 2 & 3 dialect with useful re-usable attribute processors such as xtra:utext-before and xtra:utext-after; compatible with both Thymeleaf and Thymesheet.

## Maven

Include the latest release from Maven,

		<dependency>
			<groupId>com.connect-group</groupId>
			<artifactId>thymeleaf-extras</artifactId>
			<version>1.0.0</version>
		</dependency>

Or for Thymeleaf 3, contributed by jegoossens,

		<dependency>
			<groupId>com.connect-group</groupId>
			<artifactId>thymeleaf-extras</artifactId>
			<version>2.0.1</version>
		</dependency>

xtra:utext-before
-----------------
The standard text replacement in Thymeleaf will replace *all text* and children within a node.
Often, we need to change the text but not alter child nodes.  A common occurrence of this would be Bootstrap icons.

    <p>Some text<i class="icon"></i></p>

Using the standard th:text directive, it is not possible to modify this text without also removing the icon.
"xtra:utext-before" will replace all text before the first child element, with the specified text.

    <p xtra:utext-before="'new message '">Some text<i class="icon"></i></p>

The output of the above will be,

    <p>new message <i class="icon"></i></p>

xtra:utext-after
----------------
This behaves much like "utext-before", except it modifes the text which occurs after the last child element.
This works if, for example, a bootstrap icon occurs at the beginning of a string.

    <p xtra:utext-after="' new message'"><i class="icon"></i> Some text</p>

The output of the above will be,

    <p><i class="icon"></i> new message</p>

xtra:insert-utext-before
------------------------
This attribute will add text to the beginning of an element, leaving all other contents of the element unchanged.

    <p xtra:insert-utext-before="'new message '"><i class="icon"></i> Some text</p>

The output of the above will be,

    <p>new message <i class="icon"></i> Some text</p>

xtra:strip-whitespace
---------------------
Remove any whitespace-only text nodes, for example between tags.

Possible values,
 * strip-whitespace="deep"
 * strip-whitespace="shallow"

This is useful for situations where there must not be space between nodes such as in some methods of creating navigation where CSS is styled inline.  Sometimes, unwanted whitespace is added by a "th:each" loop

    <ul>
        <li th:each="item : ${items}">Element</li>
    </ul>

... would produce,

    <ul>
        <li>Element</li>
        <li>Element</li>
        <li>Element</li>
    <ul>

To remove the whitespace between each list item, use xtra:strip-whitespace (shallow).

    <ul xtra:strip-whitespace="shallow">
        <li th:each="item : ${items}">Element</li>
    </ul>

... would produce,

    <ul><li>Element</li><li>Element</li><li>Element</li><ul>

The "deep" option will remove whitespace only text nodes of the children and all their descendants.
