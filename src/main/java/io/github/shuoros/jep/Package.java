package io.github.shuoros.jep;

import java.util.List;
import java.util.Objects;

public class Package {
    private String name;
    private String version;
    private String description;
    private List<String> dependencies;

    public Package(String name, String version, String description, List<String> dependencies) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.dependencies = dependencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package that = (Package) o;
        return Objects.equals(name, that.name) && Objects.equals(version, that.version) && Objects.equals(description, that.description) && Objects.equals(dependencies, that.dependencies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, version, description, dependencies);
    }

    @Override
    public String toString() {
        return "Package{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", dependencies=" + dependencies +
                '}';
    }
}
