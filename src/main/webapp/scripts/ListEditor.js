class ListEditor {
    constructor(templateId, containerId, binding, inital) {
        this.templateId = templateId;
        this.containerId = containerId;
        this.binding = binding;
        this.inital = inital;
    }

    add() {
        this.init(this.count(), this.inital());
        this.onAdd();
    }

    remove(b) {
        b.parentElement.parentElement.remove();
        this.adjustAllAttributes();
        this.onRemove();
    }

    init(i, o) {
        var templateNode = document.getElementById(this.templateId);
        var node = templateNode.cloneNode(true);
        node.removeAttribute("hidden");
        node.setAttribute("class", "form-section");
        this.binding(this, node, o);
        this.adjustAttributes(node, templateNode, i);
        document.getElementById(this.containerId).appendChild(node);
    }

    initField(node, o) {
        if (node.children.item(0).nodeName == "SELECT") {
            var option = document.createElement("option");
            option.selected = true;
            option.setAttribute("value", o.value);
            option.appendChild(document.createTextNode(o.value));
            node.children.item(0).appendChild(option);
        } else if (node.children.item(0).nodeName == "TEXTAREA") {
            node.children.item(0).innerHTML = o.value;
        } else {
            node.children.item(0).setAttribute("value", o.value);
        }
        node.setAttribute("class", o.valid ? "form-input" : "form-input-active form-input");
        node.children.item(1).innerHTML = o.message;
    }

    count() {
        var c = 0;
        var node = document.getElementById(this.containerId);
        for (var i in node.children) {
            if (node.children[i].nodeType == 1) {
                c++;
            }
        }
        return c;
    }

    adjustAllAttributes() {
        var div = document.getElementById(this.containerId);
        var templateNode = document.getElementById(this.templateId);
        for (var i in div.children) {
            if (div.children[i].nodeType == 1) {
                this.adjustAttributes(div.children[i], templateNode, i);
            }
        }
    }

    adjustAttributes(node, templateNode, index) {
        this.adjustAttribute(node, templateNode, "id", index);
        this.adjustAttribute(node, templateNode, "name", index);
        this.adjustAttribute(node, templateNode, "for", index);

        for (var i in node.children) {
            if (node.children[i].nodeType == 1) {
                this.adjustAttributes(node.children[i], templateNode.children[i], index);
            }
        }
    }

    adjustAttribute(node, templateNode, attributeName, index) {
        if (node.hasAttribute(attributeName)) {
            node.setAttribute(attributeName, templateNode.getAttribute(attributeName) + index);
        }
    }

    onAdd() {}

    onRemove() {}
}