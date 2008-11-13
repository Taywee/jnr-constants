$VERBOSE = true
$verbose = Rake.application.options.trace

require 'ffi'
require 'fileutils'

ICONSTANT = "com.kenai.constantine.Constant"
PLATFORM_PREFIX = "com.kenai.constantine.platform"
PLATFORM_DIR = "src/com/kenai/constantine/platform"
OS_CONSTANTS = Dir["gen/const/*.rb"].map {|c| File.basename(c, ".rb") }

arches = [ ]
arch_options = {}

if JRuby::FFI::Platform::IS_MAC
  osx_arches = [ "ppc", "i386" ]
  osx_arches << "x86_64" if `arch` == "x86_64"
  osx_arches.each do |arch|
    arches << arch
    platform_dir = File.join("#{PLATFORM_DIR}/darwin", "#{arch}")
    platform_pkg = "#{PLATFORM_PREFIX}.darwin.#{arch}"
    arch_options[arch] = {
        :platform_osdir => File.join(PLATFORM_DIR, JRuby::FFI::Platform::OS),
        :platform_dir => platform_dir,
        :platform_pkg => platform_pkg,
        :cppflags => "-arch #{arch}"
    }
  end

else
  arches = [ JRuby::FFI::Platform::ARCH ]
  arch_options[JRuby::FFI::Platform::ARCH] = {
    :platform_osdir => File.join(PLATFORM_DIR, JRuby::FFI::Platform::OS),
    :platform_dir => File.join(PLATFORM_DIR, JRuby::FFI::Platform::OS, JRuby::FFI::Platform::ARCH),
    :platform_pkg => "#{PLATFORM_PREFIX}.#{JRuby::FFI::Platform::OS}.#{JRuby::FFI::Platform::ARCH}",
    :cppflags => ""
    }
end

def gen_platform_constants(name, pkg, dir, options = {})
  out_name = File.join(dir, "#{name}.java")
  meth = "gen_#{name.downcase}_java".to_sym
  FileUtils.makedirs(dir)
  File.open(out_name, "w") do |f|
    constants = send(meth, options).constants

    f.puts "// WARNING: This file is autogenerated. DO NOT EDIT!"
    f.puts "// Generated #{Time.now}"
    f.puts "package #{pkg};"
    f.puts "public enum #{name} implements #{ICONSTANT} {";
    sep = nil
    comments = []
    sorted = constants.values.reject { |c| c.value.nil? }.sort
    min_value, max_value = sorted.first.value, sorted.last.value
    constants.values.each_with_index do |c, i|
      if c.value.nil?
        comments << "// #{c.name} not defined"
      else
        if sep
          f.puts sep
          comments.each {|comm| f.puts "#{comm}" }
          comments.clear
        else
          sep = ","
        end
        f.print "#{c.name}(#{c.converted_value})"
      end
    end
    f.puts ";"

    comments.each {|comm| f.puts "#{comm}" }
    f.puts "private final int value;"
    f.puts "private #{name}(int value) { this.value = value; }"
    f.puts "public static final int MIN_VALUE = #{min_value};"
    f.puts "public static final int MAX_VALUE = #{max_value};"
    f.puts ""
    # Generate the string description table
    unless constants.values.reject {|c| c.description.nil? }.empty?
      f.puts "static final class StringTable {"
      f.puts "  public static final java.util.Map<#{name}, String> descriptions = generateTable();"
      f.puts "  public static final java.util.Map<#{name}, String> generateTable() {"
      f.puts "    java.util.Map<#{name}, String> map = new java.util.EnumMap<#{name}, String>(#{name}.class);"
      constants.values.each do |c|
        f.puts "  map.put(#{c.name}, \"#{c.description.nil? ? c.name : c.description}\");"
      end
      f.puts "    return map;"
      f.puts "  }"
      f.puts "}"
      f.puts "public final String toString() { return StringTable.descriptions.get(this); }"
    end
    f.puts "public final int value() { return value; }"
    f.puts "}"
  end
end
def gen_fake_constants(name, pkg, dir, options = {})
  out_name = File.join(dir, "#{name}.java")
  meth = "gen_#{name.downcase}_java".to_sym
  FileUtils.makedirs(dir)
  File.open(out_name, "w") do |f|
    names = send(meth, options).names

    f.puts "// WARNING: This file is autogenerated. DO NOT EDIT!"
    f.puts "// Generated #{Time.now}"
    f.puts "package #{pkg};"
    f.puts "public enum #{name} implements #{ICONSTANT} {";
    
    names.each_with_index do |c, i|
      f.puts "#{c}(#{i + 1})#{i < (names.length - 1) ? ',' : ';'}"
    end

    f.puts "private final int value;"
    f.puts "private #{name}(int value) { this.value = value; }"
    f.puts "public final int value() { return value; }"
    f.puts "}"
  end
end
def gen_xplatform_constants(name, pkg, dir, options = {})
  out_name = File.join(dir, "#{name}.java")
  meth = "gen_#{name.downcase}_java".to_sym
  FileUtils.makedirs(dir)
  File.open(out_name, "w") do |f|
    cg = send(meth, options)
    names = cg.names

    f.puts "// WARNING: This file is autogenerated. DO NOT EDIT!"
    f.puts "// Generated #{Time.now}"
    f.puts "package #{pkg};"
    f.puts "public enum #{name} implements #{ICONSTANT} {";
    names.each { |n| f.puts "#{n}," }
    f.puts "__UNKNOWN_CONSTANT__;"
    unknown_range = cg.unknown_range
    unless unknown_range.empty?
      f.puts "private static final ConstantResolver<#{name}> resolver "
      f.puts "= ConstantResolver.getResolver(#{name}.class, #{unknown_range[:first]}, #{unknown_range[:last]});"
    else
      f.puts "private static final ConstantResolver<#{name}> resolver = ConstantResolver.getResolver(#{name}.class);"
    end
    f.puts "public final int value() { return resolver.intValue(this); }"
    f.puts "public final String description() { return resolver.description(this); }"
    f.puts "public final String toString() { return description(); }"
    f.puts "public final static #{name} valueOf(int value) { "
    f.puts "    return resolver.valueOf(value);"
    f.puts "}"
    f.puts "}"
  end
end
const_tasks = []
OS_CONSTANTS.each do |name|
  load File.join(File.dirname(__FILE__), "const", "#{name}.rb")
  pkg = "#{PLATFORM_PREFIX}.#{JRuby::FFI::Platform::OS}"
  dir = "#{PLATFORM_DIR}/#{JRuby::FFI::Platform::OS}"
  file "#{dir}/#{name}.java" do
    gen_platform_constants(name, pkg, dir)
    
  end
  file "#{PLATFORM_DIR}/#{name}.java" do
    gen_xplatform_constants(name, PLATFORM_PREFIX, PLATFORM_DIR)
  end
  file File.join(PLATFORM_DIR, "fake", "#{name}.java") do
    gen_fake_constants(name, "#{PLATFORM_PREFIX}.fake", File.join(PLATFORM_DIR, "fake"))
  end
  const_tasks << "#{dir}/#{name}.java"
  const_tasks << "#{PLATFORM_DIR}/#{name}.java"
  const_tasks << "#{PLATFORM_DIR}/fake/#{name}.java"
end unless JRuby::FFI::Platform::IS_WINDOWS


task :default => :generate
task :generate => const_tasks
task :regen => [ :clean, :generate ]
task :clean do
  OS_CONSTANTS.each do |name|
    [ File.join(PLATFORM_DIR, JRuby::FFI::Platform::OS, "#{name}.java"),
      File.join(PLATFORM_DIR, "fake", "#{name}.java"),
      File.join(PLATFORM_DIR, "#{name}.java") ].each do |file|
      FileUtils.rm_f(file)
    end
  end
end