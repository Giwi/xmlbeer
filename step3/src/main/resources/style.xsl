<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output
            method="html"
            encoding="UTF-8"
            doctype-public="-//W3C//DTD HTML 4.01//EN"
            doctype-system="http://www.w3.org/TR/html4/strict.dtd"
            indent="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Beer List</title>
                <link rel="stylesheet"
                      href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
                      integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ"
                      crossorigin="anonymous"/>
                <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js"
                        integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n"
                        crossorigin="anonymous"/>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
                        integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
                        crossorigin="anonymous"/>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
                        integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
                        crossorigin="anonymous"/>
            </head>
            <body>
                <div class="container">
                    <h1>Beer list</h1>
                    <hr/>
                    <div class="card-columns">
                        <xsl:for-each select="beers/beer">
                            <xsl:apply-templates select="."/>
                        </xsl:for-each>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="alcohol">
        <xsl:text>&#xA0;</xsl:text>
        <span class="badge badge-pill badge-primary">
            <xsl:value-of select="."/> °
        </span>
    </xsl:template>
    <xsl:template match="description">
        <p class="card-text text-justify">
            <xsl:value-of select="."/>
        </p>
    </xsl:template>
    <xsl:template match="img">
        <xsl:element name="img">
            <xsl:attribute name="class">card-img-top rounded-circle</xsl:attribute>
            <xsl:attribute name="style">max-height: 100px;</xsl:attribute>
            <xsl:attribute name="alt">
                <xsl:value-of select="."/>
            </xsl:attribute>
            <xsl:attribute name="src">https://raw.githubusercontent.com/Giwi/jquery-beer/master/<xsl:value-of
                    select="."/>
            </xsl:attribute>
        </xsl:element>
    </xsl:template>

    <xsl:template match="beer">
        <xsl:element name="div">
            <xsl:attribute name="class">card card-inverse card-info text-center</xsl:attribute>
            <xsl:if test="alcohol &lt; 7">
                <xsl:attribute name="class">card card-inverse text-center card-success</xsl:attribute>
            </xsl:if>
            <xsl:if test="alcohol &gt; 8">
                <xsl:attribute name="class">card card-inverse text-center card-danger</xsl:attribute>
            </xsl:if>
            <xsl:apply-templates select="img"/>
            <div class="card-block">
                <h4 class="card-title">
                    <xsl:value-of select="name"/>
                    <xsl:apply-templates select="alcohol"/>
                </h4>
                <xsl:apply-templates select="description"/>
            </div>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
